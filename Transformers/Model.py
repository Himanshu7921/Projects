import torch
import torch.nn as nn
import math

class InputEmbeddings(nn.Module):
    def __init__(self, vocab_size: int, model_dim: int):
        super().__init__()
        self.vocab_size = vocab_size
        self.model_dim = model_dim
        self.embedding = nn.Embedding(num_embeddings = vocab_size, embedding_dim = model_dim)
    def forward(self, x):
        return self.embedding(x) * math.sqrt(self.model_dim)

class PositionalEmbedding(nn.Module):
    def __init__(self, max_size: int, model_dim: int, dropout: float):
        self.max_size = max_size
        self.model_dim = model_dim
        self.dropout = nn.Dropout(dropout)

        # Create a random matrix (vector) of Positional Embedding of size equal to Input Embedding i.e. (model_dim X max_size)
        pe = torch.rand(model_dim, max_size)

        # create a position of each text in a corpus
        position = torch.arange(max_size).unsqueeze(dim = 1) # size = (max_size, 1)
        div_term = torch.exp(torch.arange(0, model_dim, 2) * (-math.log(10000.0) / model_dim)) # size = (1, max_size // 2)

        # Apply the sinosudial operation on `pe` vector
        pe[:, 0::2] = torch.sin(position * div_term)
        pe[:, 1::2] = torch.cos(position * div_term)

        # Adds a batch size
        pe = pe.unsqueeze(dim = 1)  # (1, max_size, model_dim)

        # Save it as a buffer
        self.register_buffer('pe', pe)
    def forward(self, x):
        return self.dropout(x + self.pe[:, :x.shape(1), :].requires_grad(False))

class LayerNormalization(nn.Module):
    def __init__(self, eps: float = 10**-9) -> None:
        super().__init__()
        self.eps = eps
        self.weight = nn.Parameter(torch.ones(1))
        self.bias = nn.Parameter(torch.ones(1))
    def forward(self, x):
        mean = x.mean(dim = -1, keepdim = True)
        std = x.std(dim = -1, keepdim = True)
        return (self.weight * ((x - mean) / std + self.eps)) + self.bias

class FeedForwardBlock(nn.Module):
    """
    Implements the position-wise feedforward network used in Transformers.
    This block introduces non-linearity between layers to enhance the model's expressive power.
    """

    def __init__(self, model_dim: int, d_ff: int, dropout: float) -> None:
        """
        Initializes the feedforward block with two linear transformations and a dropout layer.

        Args:
            model_dim (int): The input and output dimensionality of the model.
            d_ff (int): The hidden dimensionality, usually larger than model_dim.
            dropout (float): The dropout probability for regularization.
        """
        super().__init__()
        self.layer_1 = nn.Linear(model_dim, d_ff)  # Expands the feature dimension
        self.layer_2 = nn.Linear(d_ff, model_dim)  # Projects back to original dimension
        self.dropout = nn.Dropout(dropout)  # Prevents overfitting by randomly dropping activations

    def forward(self, x):
        """
        Forward pass through the feedforward block.
        
        Steps:
        1. Linear transformation: Expands the feature space from model_dim to d_ff.
        2. Activation: Applies ReLU for non-linearity.
        3. Dropout: Randomly drops activations to prevent overfitting.
        4. Linear transformation: Projects the representation back to model_dim.
        """
        return self.layer_2(self.dropout(torch.relu(self.layer_1(x))))

class MultiHeadAttention(nn.Module):
    def __init__(self, model_dim: int, dropout: float, h: int) -> None:
        super().__init__()
        assert model_dim % h == 0, f"(model_dim){model_dim} is not divisible by (h) {h}"
        self.dropout = nn.Dropout(dropout)
        self.h = h
        self.model_dim = model_dim
        self.d_k = model_dim // h # equivalent to int(model_dim / h)
        self.w_q = nn.Linear(model_dim, model_dim)
        self.w_k = nn.Linear(model_dim, model_dim)
        self.w_v = nn.Linear(model_dim, model_dim)
        self.w_o = nn.Linear(model_dim, model_dim)

    @staticmethod
    def attention(query, key, value, mask, dropout):
        d_k = query.shape[-1]

        attention_scores = query @ key.transpose(-2, -1) / math.sqrt(d_k)
        if mask is not None:
            attention_scores.masked_fill_(mask == 0, -1e9)
        attention_scores = attention_scores.softmax(dim = -1)
        if dropout is not None:
            attention_scores = dropout(attention_scores)
        return attention_scores @ value, attention_scores
        
    def forward(self, x, q, k, v, mask):
        query = self.w_q(q) # (Batch_size, num_embeddings, model_dim) --> (Batch_size, num_embeddings, model_dim)
        key = self.w_k(k) # (Batch_size, num_embeddings, model_dim) --> (Batch_size, num_embeddings, model_dim)
        value = self.w_v(v) # (Batch_size, num_embeddings, model_dim) --> (Batch_size, num_embeddings, model_dim)

        # (Batch_size, num_embeddings, model_dim) --> (Batch_size, num_embeddings, h, d_k) --> (Batch, h, num_embeddings, d_k)
        query = query.view(query.shape[0], query.shape[1], self.h, self.d_k)
        key = key.view(key.shape[0], key.shape[1], self.h, self.d_k)
        value = value.view(value.shape[0], value.shape[1], self.h, self.d_k)

        x, self.attention_scores = MultiHeadAttention().attention(query, key, value, mask, self.dropout)
        # (Batch, h, num_embeddings, d_k) --> (Batch, num_embeddings, h, d_k) --> (Batch, num_embeddings, model_dim)
        x = x.transpose(1, 2).contiguous().view(x.shape[0], x.shape[1], x.shape[2] * x.shape[3])
        x = x.transpose(1, 2).contiguous().view(x.shape[0], x.shape[1], self.h * self.d_k)
        
        # (Batch, num_embeddings, model_dim) --> (Batch, num_embeddings, model_dim)
        return self.w_o(x)
       
class ResidualConnections(nn.Module):
    def __init__(self, dropout: float) -> None:
        super().__init__()
        self.dropout = nn.Dropout(dropout)
        self.norm = LayerNormalization()

    def forward(self, x, sublayer):
        return self.norm(self.dropout(x + sublayer(x)))


class EncoderBlock(nn.Module):
    def __init__(self, self_attention_block: MultiHeadAttention, feed_forward_block: FeedForwardBlock, dropout: float):
        super().__init__()
        self.self_attention_block = self_attention_block
        self.dropout = nn.Dropout(dropout)
        self.feed_forward_block = feed_forward_block
        self.residual_connections = nn.ModuleList([ResidualConnections(dropout) for _ in range(2)])


    def forward(self, x, mask):
        x = self.residual_connections[0](x, lambda  x: self.self_attention_block(x, x, x, mask))
        x = self.residual_connections[1](x, self.feed_forward_block)
        return x

class Encoder(nn.Module):
    def __init__(self, layers: nn.ModuleList):
        super().__init__()
        self.layers = layers
        self.norm = LayerNormalization()
        
    def forward(self, x, mask):
        for layer in self.layers:
            x = layer(x, mask)
        return self.norm(x)

class DecoderBlock(nn.Module):
    def __init__(self, self_attention_block: MultiHeadAttention, cross_attention_block: MultiHeadAttention, feed_forward_block: FeedForwardBlock, dropout: float):
        super().__init__()
        self.self_attention_block = self_attention_block
        self.cross_attention_block = cross_attention_block
        self.feed_forward_block = feed_forward_block
        self.dropout = nn.Dropout(dropout)
        self.residual_connections = nn.ModuleList(ResidualConnections(dropout) for _ in range(3))
    
    def forward(self, x, encoder_output, src_mask, tgt_mask):
        x = self.residual_connections[0](x, lambda x: self.self_attention_block(x, x, x, tgt_mask))
        x = self.residual_connections[1](x, lambda x: self.self_attention_block(x, encoder_output, encoder_output, src_mask))
        x = self.residual_connections[2](x, self.feed_forward_block)
        return x

class Decoder(nn.Module):
    def __init__(self, layers: nn.ModuleList):
        super().__init__()
        self.layers = layers
        self.norm = LayerNormalization()
    
    def forward(self, x, encoder_output, src_mask, tgt_mask):
        for layer in self.layers:
            x = layer(x, encoder_output, src_mask, tgt_mask)
        return self.norm(x)

class ProjectionLayer(nn.Module):
    def __init__(self, model_dim: int, vocab_size: int):
        super().__init__()
        self.proj = nn.Linear(model_dim, vocab_size)
    
    def forward(self, x):
        # (Batch_size, num_embeddings, model_dim)  ---> (Batch_size, num_embeddings, vocab_size)
        return torch.log_softmax(self.proj(x), dim = -1) # log_softmax due to numerical stability

class Transformer(nn.Module):
    def __init__(self, encoder: Encoder, decoder: Decoder, src_embed: InputEmbeddings, tgt_embd: InputEmbeddings, src_pos: PositionalEmbedding, tgt_pos: PositionalEmbedding, projection_layer: ProjectionLayer):
        super().__init__()
        self.encoder = encoder
        self.decoder = decoder
        self.src_embed = src_embed
        self.src_pos = src_pos
        self.tgt_embd = tgt_embd
        self.tgt_pos = tgt_pos
        self.projection_layer = projection_layer
    
    def encode(self, src, src_mask):
        src = self.src_embed(src)
        src = self.src_pos(src)
        return self.encode(src, src_mask)

    def decode(self, encoder_output, src_mask, tgt, tgt_mask):
        tgt = self.tgt_embd(tgt)
        tgt = self.tgt_pos(tgt)
        return self.decode(tgt, encoder_output, src_mask, tgt_mask)
    
    def project(self, x):
        return self.projection_layer(x)

def build_transformer(src_vocab_size: int, tgt_vocab_size: int, src_seq_len: int, tgt_seq_len: int, model_dim: int = 512, N: int = 6, h: int = 8, dropout: float = 0.1, d_ff: int = 2048) -> Transformer:

    # Building embedding Layers
    src_embedding = InputEmbeddings(model_dim, src_vocab_size)
    tgt_embedding = InputEmbeddings(model_dim, tgt_vocab_size)

    # Positional Embedding Layers
    src_pos_embd = PositionalEmbedding(model_dim, src_seq_len, dropout)
    tgt_pos_embd = PositionalEmbedding(model_dim, src_seq_len, dropout)

    # Encoder - blocks
    encoder_blocks = []
    for _ in range(N):
        encoder_self_attention_block = MultiHeadAttention(model_dim, dropout, h)
        feed_forward_block = FeedForwardBlock(model_dim, d_ff, dropout)
        encoder_block = EncoderBlock(encoder_self_attention_block, feed_forward_block, dropout)
        encoder_blocks.append(encoder_block)

    # Decoder - blocks
    decoder_blocks = []
    for _ in range(N):
        decoder_self_attention_block = MultiHeadAttention(model_dim, dropout, h)
        cross_attention_block = MultiHeadAttention(model_dim, dropout, h)
        feed_forward_block = FeedForwardBlock(model_dim, d_ff, dropout)
        decoder_block = DecoderBlock(decoder_self_attention_block, cross_attention_block, feed_forward_block, dropout)
        decoder_blocks.append(decoder_block)

    # Create encoders and decoders
    encoder = Encoder(nn.ModuleList(encoder_blocks))
    decoder = Decoder(nn.ModuleList(decoder_blocks))

    # Projection Layer
    projection_layer = ProjectionLayer(model_dim, tgt_vocab_size)

    # Create the Transformer
    transformer = Transformer(encoder, decoder, src_embedding, tgt_embedding, src_pos_embd, tgt_pos_embd, projection_layer)

    # Initialize the Parameters
    for p in transformer.parameters():
        if p.dim > 1:
            nn.init.xavier_uniform_(p)

    return transformer
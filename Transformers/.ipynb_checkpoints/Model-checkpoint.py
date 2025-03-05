#!/usr/bin/env python
# coding: utf-8

# In[1]:


import torch
import torch.nn as nn
import math


# # Building a Transformer from scratch
# 
# ![image.png](attachment:0f741712-d3c7-45a9-8b4c-1732106d7476.png)

# # 1. Input Embedding
# ---
# 
# - Converts the Given Sentence into tokens and assigns a unique ID to those Tokens
# - But this dsn't take care of the Positions of the wordings, therefore we apply Positional Embedding after Embedding the inputs ans returns teh total embedding as Input Embedding + Positional Embedding
# 
# 

# In[2]:


class InputEmbeddings(nn.Module):
    def __init__(self, vocab_size: int, model_dim: int):
        super().__init__()
        self.vocab_size = vocab_size
        self.model_dim = model_dim
        self.embedding = nn.Embedding(num_embeddings = vocab_size, embedding_dim = model_dim)
    def forward(self, x):
        return self.embedding(x) * math.sqrt(model_dim)


# # 2. Positional Embedding
# 
# ---
# 
# - Takes care of the Position of the words in a given sentence
# - Example: **Cat is eating mouse** and **mouse is eating Cat** have different meanings

# In[3]:


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


# # Layer Normalization:
# 
# ---
# 
# - It adjusts the numbers coming out of a layer so that they have a consistent scale. Specifically, for each example (or data point) processed by the layer, it changes the numbers so that their average (mean) is 0 and their spread (variance) is 1.
# - layer normalization helps prevent issues like exploding or vanishing gradients. This makes the training process more stable.
# 
# ---
# 
# ![image.png](attachment:a059ecba-d776-483e-9112-6fe0aff2a20e.png)

# In[4]:


class LayerNormalization(nn.Module):
    def __init__(self, eps: float = 10**-9) -> None:
        super().__init__()
        self.eps = eps
        self.weight = nn.Parameter(torch.ones(1))
        self.bias = nn.Parameter(torch.ones(1))
    def forward(self, x):
        mean = x.mean(dim = -1, keepdim = True)
        std = x.std(dim = -1, keepdim = True)
        return (self.weight * ((x - mean) / std + self.eps)) + slef.bias


# # Feed Forward Block
# 
# ---
# 
# - Introduces non-linearity between layers to enhance the model's expressive power.
# - To Capture non-Linear Patterns
# - **Steps**
#     1. Linear transformation: Expands the feature space from **model_dim** to **d_ff**.
#     2. Activation: Applies ReLU for non-linearity.
#     3. Dropout: Randomly drops activations to prevent overfitting.
#     4. Linear transformation: Projects the representation back to **model_dim**.

# In[5]:


import torch
import torch.nn as nn

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


# # MultiHead Attention
# 
# ![image.png](attachment:9417144b-bca4-402c-b90c-6b6360391990.png)

# ![image.png](attachment:66b12ac5-17cd-4880-8ff0-80cc650ec921.png)

# ![image.png](attachment:66b12ac5-17cd-4880-8ff0-80cc650ec921.png)

# ![image.png](attachment:66b12ac5-17cd-4880-8ff0-80cc650ec921.png)

#     ┌──────────────────────┐
#     │ Input Sequence       │
#     └──────────────────────┘
#              │
#              ▼
#     ┌──────────────────────┐
#     │ Q, K, V Projections  │
#     └──────────────────────┘
#              │
#              ▼
#     ┌──────────────────────┐
#     │ Self-Attention (Multiple Heads) │
#     └──────────────────────┘
#              │
#              ▼
#     ┌──────────────────────┐
#     │ Concatenate Outputs  │
#     └──────────────────────┘
#              │
#              ▼
#     ┌──────────────────────┐
#     │ Final Linear Layer   │
#     └──────────────────────┘
# 

# In[6]:


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
        


# # **Residual Connections:**
# 
# #### **🔹 What is a Residual Connection?**
# A **residual connection** is a shortcut (or skip connection) in a deep neural network that **bypasses one or more layers** and adds the original input back to the output of the layer(s). 
# 
# Mathematically, for a transformation function F(x), a residual connection modifies the output as:
# 
# 
# output = x + F(x)
# 
# 
# where:
# - (x) is the **input** to the layer.
# - F(x) is the **output of the transformation layer** (e.g., self-attention, feed-forward).
# - The final output is **normalized** using **Layer Normalization**.
# 
# ---
# 
# ### **🔹 Why Use Residual Connections?**
# 1️⃣ **Solves Vanishing Gradient Problem**  
#    - In deep networks, gradients become very small (vanish) as they backpropagate.  
#    - Residual connections allow **gradients to flow directly** from later layers to earlier ones, preventing this issue.
# 
# 2️⃣ **Eases Training of Deep Networks**  
#    - Instead of learning a full mapping \( y = F(x) \), the network learns a **residual function** \( F(x) = y - x \).  
#    - This makes training **faster and more stable**.
# 
# 3️⃣ **Preserves Original Information**  
#    - Some layers **modify** the input too much (e.g., self-attention layers).  
#    - Adding \( x \) ensures that original information is **not lost**.
# 
# 4️⃣ **Helps in Gradient Flow**  
#    - Since the gradient can directly pass through the shortcut connection, it prevents **gradient explosion** or **vanishing gradients**.
# 
# ---
# 
# ### **🔹 Example in Transformers**
# In the **Transformer Encoder**, each **Encoder Block** consists of:
# 1. **Multi-Head Self-Attention**  
# 2. **Feed-Forward Network**  
# 3. **Residual Connections** (around both)
# 
# Each layer follows:
# \[
# x = \text{LayerNorm}(x + \text{SelfAttention}(x))
# \]
# \[
# x = \text{LayerNorm}(x + \text{FeedForward}(x))
# \]
# 
# ---
# 
# ### **🔹 Visual Representation**
# ```
#     Input → [Layer] → Add (Residual) → LayerNorm → Output
#            ↘────────────────────────↗
# ```
# 
# ---
# 
# ### **🔹 Key Takeaways**
# ✅ **Helps deep networks train faster and more efficiently.**  
# ✅ **Solves vanishing gradient problem by allowing gradients to flow directly.**  
# ✅ **Preserves original information, preventing information loss.**  
# ✅ **Used in Transformers, ResNets, and other deep learning architectures.**  
# 

# In[7]:


class ResidualConnections(nn.Module):
    def __init__(self, dropout: float) -> None:
        super().__init__()
        self.dropout = nn.Dropout(dropout)
        self.norm = LayerNormalization()

    def forward(self, x, sublayer):
        return self.norm(self.dropout(x + sublayer(x)))


# ![image.png](attachment:e999232f-854e-4cf3-86e2-9909fa3f67ba.png)

# In[8]:


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


# In[9]:


class Encoder(nn.Module):
    def __init__(self, layers: nn.ModuleList):
        super().__init__()
        self.layers = layers
        self.norm = LayerNormalization()
        
    def forward(self, x, mask):
        for layer in self.layers:
            x = layer(x, mask)
        return self.norm(x)


# In[10]:


class DecoderBlock(nn.Module):
    def __init__(self, self_attention_block: MultiHeadAttention, cross_attention: MultiHeadAttention, dropout: float):
        super().__init__()
        self.self_attention_block = self_attention_block
        self.cross_attention = cross_attention
        self.dropout = nn.Dropout(dropout)


# In[ ]:


import nbformat
from nbconvert import PythonExporter

# Load the notebook
with open("Model.ipynb", "r", encoding="utf-8") as f:
    notebook_content = nbformat.read(f, as_version=4)

# Convert to Python script
exporter = PythonExporter()
script, _ = exporter.from_notebook_node(notebook_content)

# Save as .py file
with open("Model.py", "w", encoding="utf-8") as f:
    f.write(script)


# In[ ]:





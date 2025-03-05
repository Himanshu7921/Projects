from dataset import BilingualDataset, causal_mask
import torch
import torch.nn as nn

from datasets import load_dataset
from tokenizers import Tokenizer
from tokenizers.models import WordLevel
from tokenizers.trainers import WordLevelTrainer
from tokenizers.pre_tokenizers import Whitespace

from pathlib import Path

from torch.utils.data import Dataset, DataLoader, random_split

def get_all_sentences(data_set, lang):
    for item in data_set:
        yield item['translation'][lang]

def get_or_build_tokenizer(config, data_set, lang):
    tokenizer_path = Path(config['tokenizer_file'].format(lang))
    if not Path.exists(tokenizer_path):
        tokenizer = Tokenizer(WordLevel(unk_token='[UNK]'))
        tokenizer.pre_tokenizer = Whitespace()
        trainer = WordLevelTrainer(special_tokens = ["[UNk]", "[PAD]", "[SOS]", "[EOS]"], min_frequency = 2)
        tokenizer.train_from_iterator(get_all_sentences(data_set, lang), trainer=trainer)
        tokenizer.save(str(tokenizer_path))
    else:
        tokenizer = Tokenizer.from_file(str(tokenizer_path))
    return tokenizer

def get_data_set(config):
    data_set_raw = load_dataset('opus_books', f'{config["lang_src"]}-{config["lang_tgt"]}', split = 'train')

    # Build Tokenizer
    tokenizer_src = get_or_build_tokenizer(config, data_set_raw, config['lang_src'])
    tokenizer_tgt = get_or_build_tokenizer(config, data_set_raw, config['lang_tgt'])

    # Split the data set into (90%) training and (10%) Validation datasets 
    train_data_size = int(len(data_set_raw) * 0.9)
    validation_data_size = int(len(data_set_raw) - train_data_size)
    train_data_raw, validation_data_raw = random_split(data_set_raw, [train_data_size, validation_data_size])

    train_ds = BilingualDataset(train_data_raw, tokenizer_src, tokenizer_tgt, config['lang_src'], config['lang_tgt'], config['seq_len'])
    val_ds = BilingualDataset(validation_data_raw, tokenizer_src, tokenizer_tgt, config['lang_src'], config['lang_tgt'], config['seq_len'])

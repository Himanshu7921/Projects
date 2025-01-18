import nbformat

with open("Model.ipynb", "r", encoding="utf-8") as f:
    notebook = nbformat.read(f, as_version=4)

with open("Model.py", "w", encoding="utf-8") as f:
    for cell in notebook['cells']:
        if cell['cell_type'] == 'code':
            f.write(cell['source'] + '\n\n')

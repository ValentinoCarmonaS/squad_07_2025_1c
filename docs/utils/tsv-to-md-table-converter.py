def format(x):
    x = x.strip()
    x = x.replace("\t", " | ")
    x = "| " + x + " |\n"
    return x

with  open("matriz.tsv", "r") as file:
    lines = file.readlines()
    lines = list(map(format, lines))
    with open("new.csv", "w") as new:
        new.writelines(lines)




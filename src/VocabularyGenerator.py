'''
Created on 21 abr. 2018
@author: Miguel Jiménez Gomis
@author: Ángel Igareta
'''
import sys

def main():
    if (len(sys.argv) != 3):
        print("Usage: python GeneradorVocabulario.py <filename> <outputname>")
    else:
        try:
            vocab = set()            
            fp = open(sys.argv[1], 'r', encoding='utf8')
            line = fp.readline()
            while line:
                if(line[len(line) - 1] == "\n"):     # quita los retornos de carro finales
                    line = line[:-1]
                temp = line.split(" ")
                temp = [x.lower() for x in temp]     # lo pasa todo a minuscula
                for x in temp:
                    vocab.add(x)
                line = fp.readline()
            with open(sys.argv[2], 'a', encoding='utf8') as outFile:
                for x in sorted(vocab):
                    outFile.write(x)
                    outFile.write("\n")
                outFile.close()
            
        finally:
            fp.close()



if __name__ == '__main__':
main()

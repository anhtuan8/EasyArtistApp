import os

path = '/home/love_you/Documents/Study/Mobile/EasyArtists/contents'

files = []
# r=root, d=directories, f = files
for r, d, f in os.walk(path):
    for file in f:
        if '.txt' not in file :
            if '.jpg' not in file:
                new_file = os.path.join(r, file + '.txt')
                os.rename(os.path.join(r, file), new_file)
                print(new_file)


import os


cur_path = os.getcwd()
download_link = os.path.join(cur_path,'link_download')
print(cur_path)
with open(download_link, 'r') as file_link:
    for line in file_link:
        replace_line = line.replace('https://', '_')
        part = replace_line.split('/')
        pic = part[2]
        print(pic)
        new_path = os.path.join(cur_path,pic)
        print(new_path)
        if not os.path.exists(new_path):
            os.makedirs(new_path)
        with open(os.path.join(new_path,'description.txt'), 'x') as description:
            pass
        with open(os.path.join(new_path,'detail.txt'), 'x') as detail:
            pass
import firebase_admin
from firebase_admin import credentials, firestore, storage
import os, json
import shortuuid
from pathlib import Path
import os
import random
import glob


def find_all_empty_files(folder_path):
    """Print all empty files in folder
    params: folder_path: folder contains the empty files ( if exist)
    """
    compliment_sentences = ['In nature, light creates the color. In the picture, color creates the',
                            'The beauty has no boundaries in this picture.',
                            'Your beauty is irresistible.',
                            'Such a charming picture.',
                            'Elegant picture.',
                            'My words are less to describe this picture.',
                            'Beauty lies within for those who choose to see.',
                            'The power of beauty lies within the soul.',
                            'This picture is worth a thousand words.',
                            'Beauty is power; a smile is its sword.',
                            'This place looks exotic.',
                            'I love how vibrant colors are in the picture.',
                            'Such a scenic view ,looks great.',
                            'Impressive picture.',
                            'Adorable picture and Your smile makes me Happy.',
                            'Looking Gorgeous and This picture made my day.']

    # number of empty files
    count = 0
    random.seed(5)
    num_sentences = len(compliment_sentences)
    with open('empty_file_list.txt', 'w') as file:
        for path in Path(folder_path).rglob('*.txt'):
            if os.stat(path).st_size == 0:
                # Choose a quote from compliment sentences
                random_num = random.randint(0, num_sentences - 1)
                count = count + 1
                # Write to file
                text_file = open(path, "w")
                quote = '\'' + compliment_sentences[random_num] + '\'' + ' - Anonymous'
                n = text_file.write(quote)
                text_file.close()
                # log name of empty file, each name in every line
                file.write(str(path))
                file.write("\n")

    print("Count: ", count)


class DatabaseManager:
    def __init__(self, bucket_name='easy-artist-60842.appspot.com',
                 path_to_key='easy-artist-60842-firebase-adminsdk-hits5-63e86fc81d.json'):
        cred = credentials.Certificate(path_to_key)
        default_app = firebase_admin.initialize_app(cred)
        self.db = firestore.client()
        self.bucket = storage.bucket(bucket_name)

    def _upload_blob(self, source_file_name, is_image=True):
        """Uploads a file to the bucket
            source_file_name = "local/path/to/file"
            isImage = True/False, identify which directory file will be saved
        """
        file_tag = source_file_name.split('/')[-1].split('.')[-1]
        hash_name = str(abs(hash(source_file_name)))
        if is_image:
            blob = self.bucket.blob("image_folder/" + hash_name + '.' + file_tag)
        else:
            blob = self.bucket.blob("text_folder/" + hash_name + '.' + file_tag)
        blob.upload_from_filename(source_file_name)
        blob.make_public()
        return blob.public_url

    def create_articles(self, topic_folder, topic_id, topic_name):
        """Create article collection"""
        article_names = [f for f in os.listdir(topic_folder) if os.path.isdir(os.path.join(topic_folder, f))]
        for article_name in article_names:
            article_folder = os.path.join(topic_folder, article_name)
            desc_path = os.path.join(article_folder, 'description.txt')
            detail_path = os.path.join(article_folder, 'detail.txt')
            try:
                image_path = glob.glob(article_folder + '/*.jpg')[0]
            except:
                print('Article: ',  article_folder)
                raise Exception("Error")
            desc_link = self._upload_blob(str(desc_path), False)
            detail_link = self._upload_blob(str(detail_path), False)
            image_link = self._upload_blob(str(image_path), True)
            article_id = shortuuid.uuid()

            article_doc = {
                'article_id': article_id,
                'name': article_name,
                'description': desc_link,
                'detail': detail_link,
                'topic_id': topic_id,
                'image_link': image_link,
                'topic_name': topic_name
            }
            self.db.collection(u'articles').add(article_doc)

    def create_topic_collection(self, folder_path, section_type):
        """
        Create topic collection
        folder_path: path to topic folder: ex: Artists/'Bui Xuan Phai'/
        section_type: section that topic belongs to: Artists
        """
        topic_names = [f for f in os.listdir(folder_path) if os.path.isdir(os.path.join(folder_path, f))]
        for topic_name in topic_names:
            topic_folder = os.path.join(folder_path, topic_name)
            info_path = glob.glob(topic_folder + '/*_info.txt')[0]
            info_link = self._upload_blob(str(info_path), False)
            topic_id = shortuuid.uuid()
            topic_dict = {
                'topic_id': topic_id,
                'name_topic': topic_name,
                'type': section_type,
                'topic_info': info_link
            }
            self.db.collection(u'topics').add(topic_dict)
            self.create_articles(topic_folder, topic_id, topic_name)

    def upload_data(self, folder_path):
        """Upload dataset to firestore"""
        section_types = [f for f in os.listdir(folder_path) if os.path.isdir(os.path.join(folder_path, f))]
        for section_type in section_types:
            print("In section: ", section_type)
            topic_path = os.path.join(folder_path, section_type)
            self.create_topic_collection(topic_path, section_type)


if __name__ == '__main__':
    databaseManager = DatabaseManager()
    source_path = '/home/love_you/Documents/Study/Mobile/EasyArtists/contents'
    # filename = databaseManager._upload_blob(source_path, is_image=False)
    # print(filename)
    databaseManager.upload_data(source_path)
    #find_all_empty_files(source_path)


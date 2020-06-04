import firebase_admin
from firebase_admin import credentials, firestore, storage
import os, json
import shortuuid


class DatabaseManager:
    def __init__(self, bucket_name='easy-artist-60842.appspot.com', path_to_key='easy-artist-60842-firebase-adminsdk-hits5-63e86fc81d.json'):
        cred = credentials.Certificate(path_to_key)
        default_app = firebase_admin.initialize_app(cred)
        self.db = firestore.client()
        self.bucket = storage.bucket('easy-artist-60842.appspot.com')

    def _upload_blob(self, source_file_name, is_image=True):
        """Uploads a file to the bucket
            source_file_name = "local/path/to/file"
            isImage = True/False, identify which directory file will be saved
        """
        file_tag = source_file_name.split('/')[-1].split('.')[-1]
        hash_name = str(abs(hash(source_file_name)))
        if is_image:
            blob = self.bucket.blob("images/" + hash_name + '.' + file_tag)
        else:
            blob = self.bucket.blob("text/" + hash_name + '.' + file_tag)
        blob.upload_from_filename(source_file_name)
        blob.make_public()
        return blob.public_url

    def __create_json_tree(self, local_content_path):
        """
        Create json tree for each section
         params: local_content_path: path to each folder contains content of each section
         return: json tree
        """
        sub_json_tree = {}
        # Check whether the link is file or not. If link is file, push to storage, get link
        uuid_is_not_assigned = True
        for f in os.scandir(local_content_path):
            file_name = f.path.split('/')[-1]

            if os.path.isfile(f.path):
                if f.path.endswith(".txt"):
                    download_link = self._upload_blob(f.path, is_image=False)
                else:
                    download_link = self._upload_blob(f.path, is_image=True)
                sub_json_tree[file_name] = download_link
                if uuid_is_not_assigned:
                    sub_json_tree["uuid"] = shortuuid.uuid()
                    uuid_is_not_assigned = False
            else:
                sub_json_tree[file_name] = self.__create_json_tree(f.path)

        return sub_json_tree

    def create_new_database(self, root_content_path):
        """
        Create database on firestore
        Args:
            root_content_path: path to folder contains all sections

        Returns:
            isSuccess: whether complete create database or not
        """
        section_paths = [f.path for f in os.scandir(root_content_path) if f.is_dir()]
        for section_path in section_paths:
            try:
                current_folder = section_path.split('/')[-1]
                section_dict = self.__create_json_tree(section_path)
                print(section_dict)
                doc_ref = self.db.collection(u'contents').document(current_folder)
                doc_ref.set(
                    section_dict
                )
            except Exception as e:
                print(e)
                return False
        return True


if __name__ == '__main__':
    databaseManager = DatabaseManager()
    source_path = '/home/love_you/Documents/Study/Mobile/EasyArtists/contents'
    # filename = databaseManager._upload_blob(source_path, is_image=False)
    # print(filename)
    databaseManager.create_new_database(source_path)


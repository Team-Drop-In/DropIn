import { useCallback } from "react";
import { useDropzone } from "react-dropzone";
import styled from "styled-components";
import { TiCameraOutline } from "react-icons/ti";

const ImageUpload = ({ imageUrl, setImageUrl, imageData, setImageData }) => {
  const onDrop = useCallback(
    (acceptedFiles) => {
      const file = acceptedFiles[0];
      const imageUrl = URL.createObjectURL(file);
      setImageUrl(imageUrl);
      const formData = new FormData();
      formData.append("image", acceptedFiles[0]);
      setImageData(formData);
    },
    // eslint-disable-next-line
    [imageData, imageUrl]
  );
  const { getRootProps, getInputProps, isDragActive } = useDropzone({ onDrop });

  return (
    <UploadContainer {...getRootProps()}>
      <input {...getInputProps()} />
      {isDragActive ? (
        <p>Drop the files here ...</p>
      ) : (
        <div>
          {imageUrl ? (
            <img src={imageUrl} alt="Preview" />
          ) : (
            <DropZoneContainer>
              <TiCameraOutline size={50} color="gray" />
              <p>
                Drag files or <br /> Click to upload
              </p>
            </DropZoneContainer>
          )}
        </div>
      )}
    </UploadContainer>
  );
};

export default ImageUpload;

const UploadContainer = styled.div`
  width: 150px;
  height: 150px;

  > div {
    width: 100%;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    border-radius: 50%;
    overflow: hidden;

    > img {
      width: 150px;
      height: 150px;
      object-fit: contain;
    }
  }
`;

const DropZoneContainer = styled.div`
  width: 100%;
  height: 100px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  > p {
    margin-top: 15px;
    text-align: center;
  }
`;

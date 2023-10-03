import styled from "styled-components";
import { COLOR } from "../../styles/theme";

const CommentForm = () => {
  return (
    <Wrap>
      <CmCount>댓글 (43)</CmCount>
      <CmForm>
        <UserAndWrite>
          <User>
            <Imgbox>
              <img src="http://via.placeholder.com/30x30" alt="" />
            </Imgbox>
            닉네임
          </User>
          <button>작성하기</button>
        </UserAndWrite>
        <Comment />
      </CmForm>
    </Wrap>
  );
};

export default CommentForm;

const Wrap = styled.section`
  width: 100%;
  min-height: 100px;
  height: fit-content;
  display: flex;
  flex-direction: column;
`;
const CmCount = styled.div`
  padding: 8px 8px;
`;
const CmForm = styled.form`
  padding: 12px 12px;
  min-height: 150px;
  height: fit-content;
  border-radius: 10px;
  border: 1px solid ${COLOR.border_grey};
  display: flex;
  flex-direction: column;
`;

const UserAndWrite = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;

  span,
  p {
    color: ${COLOR.main_grey};
  }

  button {
    background-color: ${COLOR.main_grey};
    padding: 8px 12px;
    border-radius: 5px;
    color: black;
    font-weight: bold;
    font-size: 0.9rem;
  }
`;
const User = styled.div`
  display: flex;
  align-items: center;
`;

const Imgbox = styled.div`
  width: 30px;
  height: 30px;
  background-color: grey;
  border-radius: 50%;
  overflow: hidden;
  margin-right: 5px;

  img {
    width: 30px;
    height: 30px;
    object-fit: contain;
  }
`;
const Comment = styled.textarea`
  margin-top: 10px;
  min-height: 95px;
  height: fit-content;
  resize: none;
  background-color: white;
  outline: none;
  padding: 8px 8px;
  font-size: 0.9rem;
  border-radius: 5px;
  /* white-space: pre; */
`;

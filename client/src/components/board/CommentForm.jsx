import styled from "styled-components";
import { useState, useEffect } from "react";
import { COLOR } from "../../styles/theme";
import { useRecoilValue } from "recoil";
import { commentsDataState, loginState } from "../../atoms/atom";
import { BiSolidUser } from "react-icons/bi";
import { useRecoilState } from "recoil";
import { createComment } from "../../apis/api";
import { toast } from "react-toastify";

const CommentForm = ({ boardData, boardId }) => {
  const [isLogin, setLogin] = useRecoilState(loginState);
  const commentsData = useRecoilValue(commentsDataState);
  const [userData, setUserData] = useState({});
  const [content, setContent] = useState("");
  const [errorMsg, setErrorMsg] = useState("댓글을 입력해주세요");

  useEffect(() => {
    if (boardData.loginUserInfo) {
      setUserData(boardData.loginUserInfo);
    }
  }, [boardData]);

  const postComment = () => {
    const data = {
      body: content,
    };

    if (content === "") {
      setErrorMsg("입력된 댓글이 없습니다");
    }

    createComment(boardId, data)
      .then(() => {})
      .catch((error) => {
        if (error && error.status === 401) {
          toast.warning("로그인이 필요합니다");
          setLogin(false);
          localStorage.removeItem("accessToken");
          localStorage.removeItem("refreshToken");
          localStorage.removeItem("accessTokenExpiration");
        }
      });
  };

  return (
    <Wrap>
      <>
        <CmCount>댓글 ({commentsData.length})</CmCount>
        <CmForm>
          <UserAndWrite>
            <User>
              <Imgbox>
                {userData.profileImageUrl ? (
                  <img src={`${userData.profileImageUrl}`} alt="" />
                ) : (
                  <BiSolidUser size={22} color={COLOR.main_yellow} />
                )}
              </Imgbox>
              {isLogin ? (
                <>{userData && userData.nickname}</>
              ) : (
                "로그인이 필요합니다"
              )}
            </User>
            <button onClick={postComment}>작성하기</button>
          </UserAndWrite>
          <Comment
            placeholder={errorMsg}
            onChange={(e) => setContent(e.target.value)}
          />
        </CmForm>
      </>
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
  display: flex;
  justify-content: center;
  align-items: center;
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

import styled from "styled-components";
import { COLOR } from "../../styles/theme";
import { FiThumbsUp } from "react-icons/fi";
import { FaThumbsUp } from "react-icons/fa";
import { BsEye } from "react-icons/bs";
import { Link, useNavigate } from "react-router-dom";
import { boardDataState, boardLikeState, loginState } from "../../atoms/atom";
import { useRecoilValue, useSetRecoilState } from "recoil";
import { useEffect, useState } from "react";
import { deleteBoard, likeBoard } from "../../apis/api";
import { toast } from "react-toastify";

const MainText = ({ boardId }) => {
  const boardData = useRecoilValue(boardDataState);
  const [viewData, setViewData] = useState({});
  const setBoardLikeState = useSetRecoilState(boardLikeState);
  const setLogin = useSetRecoilState(loginState);
  const navigate = useNavigate();

  const formatDate = (createdDate) => {
    const currentDate = new Date();
    const date = new Date(createdDate);
    const timeDifference = currentDate - date;

    if (timeDifference > 24 * 60 * 60 * 1000) {
      //24시간 이후
      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, "0");
      const day = String(date.getDate()).padStart(2, "0");
      return `${year}-${month}-${day}`;
    } else {
      //24시간 이내
      const hoursAgo = Math.floor(timeDifference / (60 * 60 * 1000));
      return `${hoursAgo}시간 전`;
    }
  };

  const handleButtonDelete = () => {
    deleteBoard(boardId)
      .then(() => {
        navigate("/board");
      })
      .catch((error) => {
        if (error && error.status === 401) {
          toast.warning("로그인이 필요합니다");
          setLogin(false);
          localStorage.removeItem("accessToken");
          localStorage.removeItem("refreshToken");
          localStorage.removeItem("accessTokenExpiration");
        } else {
          toast.warning("게시글 삭제 실패");
        }
      });
  };

  const handleLike = () => {
    const data = {
      likeCategoryId: boardId,
      likeCategory: "POST",
    };
    likeBoard(data)
      .then(() => {
        setBoardLikeState(true);
      })
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

  useEffect(() => {
    setViewData(boardData);
  }, [boardData]);

  return (
    <Wrap>
      {viewData.body && (
        <>
          <Head>
            <NameAndInfo>
              <User>
                <Imgbox>
                  <img src={`${viewData.profileImageUrl}`} alt="" />
                </Imgbox>
                <Link to={`/profile/${viewData.writer.id}`}>
                  {viewData.writer.nickname}
                </Link>
              </User>
              <ViewAndLike>
                <span>
                  <BsEye />
                  {viewData.viewCount}
                </span>
                <button>
                  <span>
                    {viewData.checkPostLike ? (
                      <FaThumbsUp />
                    ) : (
                      <FiThumbsUp onClick={handleLike} />
                    )}
                    {viewData.likeCount}
                  </span>
                </button>
              </ViewAndLike>
            </NameAndInfo>
            <TitleAndTime>
              <span>{viewData.title}</span>
              <div>
                <p>{formatDate(viewData.createdDate)}</p>
                <ModifyBtn>
                  {viewData.checkWriter && (
                    <>
                      <Link to={`/board/${boardId}/edit`}>수정</Link>|
                      <p onClick={handleButtonDelete}>삭제</p>
                    </>
                  )}
                </ModifyBtn>
              </div>
            </TitleAndTime>
          </Head>
          <Body>{viewData.body}</Body>
        </>
      )}
    </Wrap>
  );
};

export default MainText;

const Wrap = styled.section`
  display: flex;
  flex-direction: column;
  width: 100%;
  margin-top: 20px;

  span,
  p {
    color: ${COLOR.main_grey};
  }
`;

const Head = styled.div`
  padding-bottom: 10px;
  border-bottom: 1px solid ${COLOR.border_grey};

  & > div {
    padding: 0px 8px;
  }
`;

const NameAndInfo = styled.div`
  height: 40px;
  display: flex;
  justify-content: space-between;
  align-items: center;

  span {
    color: ${COLOR.main_grey};
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

const ViewAndLike = styled.div`
  font-size: 0.9rem;
  display: flex;

  button {
    background-color: transparent;
    color: ${COLOR.main_grey};
    cursor: pointer;
    padding: 0px 0px 0px 6px;
  }
  svg {
    margin-right: 3px;
  }

  span {
    display: flex;
    justify-content: center;
    align-items: center;
  }
`;

const TitleAndTime = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 35px;

  span {
    font-size: 1.4rem;
    line-height: 1rem;
    font-weight: bold;
    letter-spacing: 1px;
  }

  p {
    font-size: 0.9rem;
    text-align: right;
    padding-right: 4px;
    margin-bottom: 8px;
  }

  & > div {
    display: flex;
    flex-direction: column;
  }
`;

const Body = styled.div`
  padding: 12px 8px;
  min-height: 380px;
  height: fit-content;
`;

const ModifyBtn = styled.div`
  width: 100%;
  display: flex;

  a {
    bottom: 5px;
    width: 2.2rem;
    height: 1.2rem;
    color: ${COLOR.main_grey};
    background-color: transparent;
    font-size: 0.9rem;
    text-align: center;
  }
  p {
    margin-left: 4px;
  }
`;

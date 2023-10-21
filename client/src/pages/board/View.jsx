import styled from "styled-components";
import { useEffect } from "react";
import { Container, Content } from "../../styles/style";
import MainText from "../../components/board/MainText";
import CommentForm from "../../components/board/CommentForm";
import CommentList from "../../components/board/CommentList";
import { getBoard } from "../../apis/api";
import { useParams, useNavigate } from "react-router-dom";
import { useRecoilState, useSetRecoilState } from "recoil";
import {
  boardDataState,
  commentsDataState,
  loginState,
} from "../../atoms/atom";

import { toast } from "react-toastify";

const View = () => {
  const { boardId } = useParams();
  const [boardData, setBoardData] = useRecoilState(boardDataState);
  const [commentsData, setCommentsData] = useRecoilState(commentsDataState);
  const navigate = useNavigate();
  const setLogin = useSetRecoilState(loginState);

  useEffect(() => {
    const fetchListData = async () => {
      try {
        const res = await getBoard(boardId);
        setBoardData(res.data);
        setCommentsData(res.data.comments);
      } catch (error) {
        if (error && error.status === 401) {
          toast.warning("로그인이 필요합니다");
          setLogin(false);
          navigate("/login");
          localStorage.removeItem("accessToken");
          localStorage.removeItem("refreshToken");
          localStorage.removeItem("accessTokenExpiration");
        }
      }
    };

    fetchListData();
  }, [boardId, navigate, setBoardData, setCommentsData, setLogin]);

  console.log(boardData);

  return (
    <Container>
      <Contain>
        <MainText boardId={boardId} />
        <CommentForm boardData={boardData} boardId={boardId} />
        <CommentList
          boardId={boardId}
          commentsData={commentsData}
          setCommentsData={setCommentsData}
        />
      </Contain>
    </Container>
  );
};

export default View;

const Contain = styled(Content)`
  min-height: calc(100vh - 60px);
  height: fit-content;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  margin-bottom: 20px;

  p,
  h1,
  h2,
  h3,
  span {
    color: white;
  }
`;

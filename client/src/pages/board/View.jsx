import styled from "styled-components";
import { useEffect } from "react";
import { Container, Content } from "../../styles/style";
import MainText from "../../components/board/MainText";
import CommentForm from "../../components/board/CommentForm";
import CommentList from "../../components/board/CommentList";
import { getBoard } from "../../apis/api";
import { useParams } from "react-router-dom";
import { useSetRecoilState } from "recoil";
import { boardDataState, commentsDataState } from "../../atoms/atom";

const View = () => {
  const { boardId } = useParams();
  const setBoardData = useSetRecoilState(boardDataState);
  const setCommentsData = useSetRecoilState(commentsDataState);

  useEffect(() => {
    const fetchListData = async () => {
      try {
        const res = await getBoard(boardId);
        setBoardData(res.data);
        setCommentsData(res.data.comments);
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };

    fetchListData();
  }, [boardId, setBoardData, setCommentsData]);

  return (
    <Container>
      <Contain>
        <MainText boardId={boardId} />
        <CommentForm />
        <CommentList />
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
`;

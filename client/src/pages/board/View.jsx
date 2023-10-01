import styled from "styled-components";
import { Container, Content } from "../../styles/style";
import MainText from "../../components/board/MainText";
import CommentForm from "../../components/board/CommentForm";

const View = () => {
  return (
    <Container>
      <Contain>
        <MainText />
        <CommentForm />
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

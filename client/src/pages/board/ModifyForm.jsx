import styled from "styled-components";
import { Container, Content } from "../../styles/style";
const Modify = () => {
  return (
    <Container>
      <Contain>하이</Contain>
    </Container>
  );
};

export default Modify;

const Contain = styled(Content)`
  height: calc(100vh - 60px);
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
`;

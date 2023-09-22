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
  height: 100vh;
`;

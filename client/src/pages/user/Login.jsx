import styled from "styled-components";
import { container, content } from "../../styles/style";

const Login = () => {
  return (
    <Container>
      <Content>
        <form></form>
      </Content>
    </Container>
  );
};

export default Login;

const Container = styled(container)``;

const Content = styled(content)`
  height: 100vh;
  background-color: pink;
`;

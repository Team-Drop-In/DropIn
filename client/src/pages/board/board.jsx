import { useState } from "react";
import axios from "axios";
import styled from "styled-components";
import { container, content } from "../../styles/style";

const Board = () => {
  const [apiResponse, setApiResponse] = useState("");

  const testApi = async () => {
    try {
      const response = await axios.get(
        "http://ec2-43-202-64-101.ap-northeast-2.compute.amazonaws.com:8080/hello"
      );
      setApiResponse(response.data);
    } catch (error) {
      console.error("API Error:", error);
    }
  };

  return (
    <Container>
      <Content>
        <button onClick={testApi}>API 요청 보내기</button>
        <div>API 응답: {apiResponse}</div>
      </Content>
    </Container>
  );
};

export default Board;

const Container = styled(container)`
  label {
    display: none;
  }
`;

const Content = styled(content)`
  height: 100vh;
  display: flex;
  flex-direction: column;
`;

import ReactQuill from "react-quill";
import { useState } from "react";
import styled from "styled-components";
import { Container, Content } from "../../styles/style";

const WriteForm = () => {
  return (
    <Container>
      <Contain>
        <ReactQuill />
      </Contain>
    </Container>
  );
};

export default WriteForm;

const Contain = styled(Content)`
  height: calc(100vh - 60px);
  display: flex;
  flex-direction: column;
  justify-content: flex-start;

  .quill {
    padding: 0px 8px;
    width: 100%;
  }
  .ql-container {
    min-height: 400px;
  }
`;

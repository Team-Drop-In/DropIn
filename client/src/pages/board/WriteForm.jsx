import ReactQuill from "react-quill";
import { useState } from "react";
import styled from "styled-components";
import { COLOR } from "../../styles/theme";
import { Container, Content } from "../../styles/style";

const WriteForm = () => {
  return (
    <Container>
      <Contain>
        <Form>
          <Title placeholder="제목을 입력해주세요 (최대 50자)" />
          <ReactQuill />
          <button></button>
        </Form>
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
    width: 100%;
    color: white;
  }
  .ql-container {
    min-height: 400px;
  }

  .ql-snow .ql-stroke {
    fill: none;
    stroke: white;
    stroke-linecap: round;
    stroke-linejoin: round;
    stroke-width: 2;
  }

  .ql-picker-label,
  .ql-editor {
    color: white;
  }

  .ql-snow.ql-toolbar button:hover,
  .ql-snow .ql-toolbar button:hover,
  .ql-snow.ql-toolbar button:focus,
  .ql-snow .ql-toolbar button:focus,
  .ql-snow.ql-toolbar button.ql-active,
  .ql-snow .ql-toolbar button.ql-active,
  .ql-snow.ql-toolbar .ql-picker-label:hover,
  .ql-snow .ql-toolbar .ql-picker-label:hover,
  .ql-snow.ql-toolbar .ql-picker-label.ql-active,
  .ql-snow .ql-toolbar .ql-picker-label.ql-active,
  .ql-snow.ql-toolbar .ql-picker-item:hover,
  .ql-snow .ql-toolbar .ql-picker-item:hover,
  .ql-snow.ql-toolbar .ql-picker-item.ql-selected,
  .ql-snow .ql-toolbar .ql-picker-item.ql-selected {
    color: ${COLOR.main_yellow};
  }

  .ql-snow.ql-toolbar button:hover,
  .ql-snow .ql-toolbar button:hover,
  .ql-snow.ql-toolbar button:focus,
  .ql-snow .ql-toolbar button:focus,
  .ql-snow.ql-toolbar button.ql-active,
  .ql-snow .ql-toolbar button.ql-active,
  .ql-snow.ql-toolbar .ql-picker-label:hover,
  .ql-snow .ql-toolbar .ql-picker-label:hover,
  .ql-snow.ql-toolbar .ql-picker-label.ql-active,
  .ql-snow .ql-toolbar .ql-picker-label.ql-active,
  .ql-snow.ql-toolbar .ql-picker-item:hover,
  .ql-snow .ql-toolbar .ql-picker-item:hover,
  .ql-snow.ql-toolbar .ql-picker-item.ql-selected,
  .ql-snow .ql-toolbar .ql-picker-item.ql-selected {
    color: ${COLOR.main_yellow};
  }
`;

const Form = styled.form`
  width: 100%;
  margin-top: 50px;

  button {
  }
`;

const Title = styled.input`
  margin: 25px 0px;
  width: 100%;
  height: 40px;
  border-radius: 10px;
  outline: none;
  padding: 10px;
  letter-spacing: 0.5px;
`;

import ReactQuill from "react-quill";
import { useState } from "react";
import styled from "styled-components";
import { COLOR } from "../../styles/theme";
import { createBoard } from "../../apis/api";
import { Container, Content } from "../../styles/style";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import { loginState } from "../../atoms/atom";
import { useSetRecoilState } from "recoil";

const WriteForm = () => {
  const setLogin = useSetRecoilState(loginState);
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const navigate = useNavigate();

  const postBoard = () => {
    const data = {
      title: title,
      body: content,
      category: "QUESTION",
    };
    createBoard(data)
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
        }
      });
  };

  return (
    <Container>
      <Contain>
        <Form>
          <Title
            placeholder="제목을 입력해주세요 (최대 50자)"
            onChange={(e) => setTitle(e.target.value)}
          />
          <ReactQuill onChange={setContent} />
          <SubmitBtn>
            <button type="button" onClick={postBoard}>
              등록
            </button>
          </SubmitBtn>
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

    .ql-editor p {
      color: white;
    }
  }
  .ql-container {
    min-height: 35rem;
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

const SubmitBtn = styled.div`
  width: 100%;
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;

  button {
    width: 6rem;
    height: 2rem;
    font-size: 1.1rem;
    border-radius: 5px;
    cursor: pointer;

    &:hover {
      background-color: ${COLOR.main_yellow};
    }
    &:active {
      background-color: ${COLOR.active_yellow};
    }
  }
`;

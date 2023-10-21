import ReactQuill from "react-quill";
import { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import styled from "styled-components";
import { COLOR } from "../../styles/theme";
import { Container, Content } from "../../styles/style";
import { boardDataState } from "../../atoms/atom";
import { useRecoilValue, useSetRecoilState } from "recoil";
import { loginState } from "../../atoms/atom";
import { Link } from "react-router-dom";
import { getBoard, updateBoard } from "../../apis/api";
import { toast } from "react-toastify";

const ModifyForm = () => {
  const { boardId } = useParams();
  const setLogin = useSetRecoilState(loginState);
  const boardData = useRecoilValue(boardDataState);
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [error, setError] = useState(false);
  const [errorMsg, setErrorMsg] = useState("");
  const navigate = useNavigate();

  const modifyBoard = () => {
    const data = {
      title: title,
      body: content,
      category: "QUESTION",
    };

    if (title === "") {
      setError(true);
      setErrorMsg("제목을 입력해주세요");
    } else if (content === "<p><br></p>") {
      setError(true);
      setErrorMsg("내용을 입력해주세요");
    } else {
      updateBoard(boardId, data)
        .then(() => {
          navigate(`/board/${boardId}`);
        })
        .catch((error) => {
          if (error && error.status === 401) {
            toast.warning("로그인이 필요합니다");
            setLogin(false);
            navigate("/login");
            localStorage.removeItem("accessToken");
            localStorage.removeItem("refreshToken");
            localStorage.removeItem("accessTokenExpiration");
          }
        });
    }
  };

  useEffect(() => {
    const fetchListData = async () => {
      try {
        const res = await getBoard(boardId);
        setTitle(res.data.title);
        setContent(res.data.body);
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };

    fetchListData();
  }, [boardId]);

  return (
    <Container>
      {boardData && (
        <Contain>
          <Form>
            <Title
              value={title}
              placeholder="제목을 입력해주세요 (최대 50자)"
              onChange={(e) => setTitle(e.target.value)}
            />
            <ReactQuill value={content} onChange={setContent} />
            {error && <ErrorMsg>{errorMsg}</ErrorMsg>}
            <SubmitBtn>
              <Link to="/board">취소</Link>
              <button type="button" onClick={modifyBoard}>
                수정
              </button>
            </SubmitBtn>
          </Form>
        </Contain>
      )}
    </Container>
  );
};

export default ModifyForm;

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
    min-height: 35rem;

    .ql-editor p,
    h1,
    h2,
    h3,
    span {
      color: white;
    }
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
  background-color: ${COLOR.main_grey};
`;

const SubmitBtn = styled.div`
  width: 100%;
  margin-top: 20px;
  display: flex;
  justify-content: space-between;

  a {
    display: flex;
    justify-content: center;
    align-items: center;
    color: #393939;
  }

  button,
  a {
    width: 6rem;
    height: 2rem;
    font-size: 1.1rem;
    border-radius: 5px;
    cursor: pointer;
    background-color: ${COLOR.main_grey};

    &:hover {
      background-color: ${COLOR.main_yellow};
    }
    &:active {
      background-color: ${COLOR.active_yellow};
    }
  }
`;

const ErrorMsg = styled.div`
  margin-top: 20px;
  width: 100%;
  color: ${COLOR.main_yellow};
`;

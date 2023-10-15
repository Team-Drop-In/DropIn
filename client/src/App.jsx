import { Suspense, lazy } from "react";
import { Routes, Route } from "react-router-dom";
import styled from "styled-components";
import "react-toastify/dist/ReactToastify.css";
import { ToastContainer } from "react-toastify";
import Header from "./components/common/Header";
import Menu from "./components/common/Menu";
import { ModalState } from "./atoms/atom";
import { useRecoilValue } from "recoil";
import TokenPage from "./controller/Token";
const Main = lazy(() => import("./pages/Main"));
const Login = lazy(() => import("./pages/user/Login"));
const Signup = lazy(() => import("./pages/user/Signup"));
const FindPwd = lazy(() => import("./pages/user/FindPwd"));
// const FindEmail = lazy(() => import("./pages/user/FindEmail"));
const ChangePwd = lazy(() => import("./pages/user/ChangePwd"));
const Leave = lazy(() => import("./pages/user/Leave"));
const LeaveConfirm = lazy(() => import("./pages/user/LeaveConfirm"));
const Profile = lazy(() => import("./pages/user/Profile"));
const Mypage = lazy(() => import("./pages/user/Mypage"));
const List = lazy(() => import("./pages/board/List"));
const View = lazy(() => import("./pages/board/View"));
const WriteForm = lazy(() => import("./pages/board/WriteForm"));
const ModifyForm = lazy(() => import("./pages/board/ModifyForm"));

const App = () => {
  const isOpenModal = useRecoilValue(ModalState);

  return (
    <div>
      <Suspense fallback={<div>Loading</div>}>
        <Header />
        {isOpenModal ? <Menu /> : null}
        <Routes>
          <Route path="/" element={<Main />} />
          <Route path="/token" element={<TokenPage />} />
          <Route path="/login" element={<Login />} />
          <Route path="/signup" element={<Signup />} />
          <Route path="/profile" element={<Profile />} />
          <Route path="/mypage" element={<Mypage />} />
          <Route path="/findpwd" element={<FindPwd />} />
          {/* <Route path="/findemail" element={<FindEmail />} /> */}
          <Route path="/changePwd" element={<ChangePwd />} />
          <Route path="/leave" element={<Leave />} />
          <Route path="/leaveconfirm" element={<LeaveConfirm />} />
          <Route path="/board" element={<List />} />
          <Route path="/board/:boardId" element={<View />} />
          <Route path="/board/write" element={<WriteForm />} />
          <Route path="/board/:boardId/edit" element={<ModifyForm />} />
        </Routes>
      </Suspense>
      <Toast
        position="top-right" // 알람 위치 지정
        autoClose={3000} // 자동 off 시간
        hideProgressBar={false} // 진행시간바 숨김
        closeOnClick // 클릭으로 알람 닫기
        rtl={false} // 알림 좌우 반전
        pauseOnFocusLoss // 화면을 벗어나면 알람 정지
        draggable // 드래그 가능
        pauseOnHover // 마우스를 올리면 알람 정지
        theme="light"
        limit={1} // 알람 개수 제한
      />
    </div>
  );
};

export default App;

export const Toast = styled(ToastContainer)`
  .Toastify__toast {
    color: #fff;
    background: rgba(55, 55, 55, 0.5);
    backdrop-filter: blur(8px);
  }

  .Toastify__toast-icon {
    width: 22px;
    height: 22px;
  }
`;

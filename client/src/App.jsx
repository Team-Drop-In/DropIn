import { Suspense, lazy } from "react";
import { Routes, Route } from "react-router-dom";
import Header from "./components/common/Header";
import Menu from "./components/common/Menu";
import { ModalState } from "./atoms/atom";
import { useRecoilValue } from "recoil";
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

const App = () => {
  const isOpenModal = useRecoilValue(ModalState);

  return (
    <div>
      <Suspense fallback={<div>Loading</div>}>
        <Header />
        {isOpenModal ? <Menu /> : null}
        <Routes>
          <Route path="/" element={<Main />} />
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
        </Routes>
      </Suspense>
    </div>
  );
};

export default App;

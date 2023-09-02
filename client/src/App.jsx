import { Suspense, lazy } from "react";
import { Routes, Route } from "react-router-dom";
import Header from "./components/common/Header";
const Login = lazy(() => import("./pages/user/Login"));
const Signup = lazy(() => import("./pages/user/Signup"));
const FindPwd = lazy(() => import("./pages/user/FindPwd"));
const FindEmail = lazy(() => import("./pages/user/FindEmail"));
const ChangePwd = lazy(() => import("./pages/user/ChangePwd"));
const Leave = lazy(() => import("./pages/user/Leave"));
const Profile = lazy(() => import("./pages/user/Profile"));
const Board = lazy(() => import("./pages/board/Board"));

const App = () => {
  return (
    <div>
      <Header />
      <Suspense fallback={<div>Loading</div>}>
        <Routes>
          <Route path="/" element={<div>홈</div>} />
          <Route path="/login" element={<Login />} />
          <Route path="/signup" element={<Signup />} />
          <Route path="/profile" element={<Profile />} />
          <Route path="/findpwd" element={<FindPwd />} />
          <Route path="/findemail" element={<FindEmail />} />
          <Route path="/changePwd" element={<ChangePwd />} />
          <Route path="/leave" element={<Leave />} />
          <Route path="/board" element={<Board />} />
        </Routes>
      </Suspense>
    </div>
  );
};

export default App;

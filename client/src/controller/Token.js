import { useEffect, useMemo } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import { loginState } from "../atoms/atom";
import { useSetRecoilState } from "recoil";

const TokenPage = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const setLogin = useSetRecoilState(loginState);

  const searchParams = useMemo(
    () => new URLSearchParams(location.search),
    [location.search]
  );

  useEffect(() => {
    try {
      const accessToken = searchParams.get("access_token");
      const refreshToken = searchParams.get("refresh_token");

      if (accessToken && refreshToken) {
        localStorage.setItem("accessToken", `Bearer ${accessToken}`);
        localStorage.setItem("refreshToken", `Bearer ${refreshToken}`);

        setLogin(true);
        navigate("/");
      } else {
        navigate("/login");
      }
    } catch (error) {
      console.error("Error while setting localStorage:", error);
    }
  }, [navigate, searchParams, setLogin]);
};
export default TokenPage;

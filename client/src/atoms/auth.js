import { atom } from "recoil";

const getInitialLoginState = () => {
  const accessToken = localStorage.getItem("accessToken");
  return !!accessToken;
};

export const loginState = atom({
  key: "loginState",
  default: getInitialLoginState(),
});

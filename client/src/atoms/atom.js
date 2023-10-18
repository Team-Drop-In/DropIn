import { atom } from "recoil";

const getInitialLoginState = () => {
  const accessToken = localStorage.getItem("accessToken");
  return !!accessToken;
};

export const loginState = atom({
  key: "loginState",
  default: getInitialLoginState(),
});

export const ModalState = atom({
  key: "ModalState",
  default: false,
});

export const boardDataState = atom({
  key: "boardData",
  default: {},
});

export const boardLikeState = atom({
  key: "boardLikeState",
  default: false,
});

export const commentsDataState = atom({
  key: "commentsData",
  default: [],
});

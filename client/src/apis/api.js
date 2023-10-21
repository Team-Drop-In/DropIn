import axios from "axios";

const baseURL = process.env.REACT_APP_BaseURL;

const api = axios.create({
  baseURL: baseURL,
  headers: {
    "Content-Type": "application/json",
  },
  withCredentials: true,
});

api.interceptors.request.use(
  (config) => {
    const accessToken = localStorage.getItem("accessToken");
    if (accessToken) {
      config.headers.Authorization = accessToken;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

const axiosWithoutToken = axios.create({
  baseURL: api.defaults.baseURL,
  headers: {
    "Content-Type": "application/json",
  },
  withCredentials: true,
});

const axiosFormdata = axios.create({
  baseURL: baseURL,
  headers: {
    "Content-Type": "multipart/form-data",
  },
  withCredentials: true,
});

// 회원
export const loginApi = async (data) => {
  try {
    const res = await api.post("/api/login", data);
    const accessToken = res.headers["authorization"];
    localStorage.setItem("accessToken", accessToken);
    // 만료시간 설정
    const expirationTime = new Date();
    expirationTime.setMinutes(expirationTime.getMinutes() + 60);
    localStorage.setItem("accessTokenExpiration", expirationTime);
  } catch (error) {
    throw error.response;
  }
};

export const googleloginApi = () => {
  window.location.assign(`${baseURL}/oauth2/authorization/google`);
};

export const signupApi = async (data) => {
  try {
    const res = await api.post("/api/member", data);
    return res;
  } catch (error) {
    throw error.response;
  }
};

export const duplicateEmailApi = async (data) => {
  try {
    const response = await api.post("/api/check-duplicate/email", data);
    return response.data;
  } catch (error) {
    throw error.response;
  }
};

export const duplicateNicknameApi = async (data) => {
  try {
    const response = await api.post("/api/check-duplicate/nickname", data);
    return response.data;
  } catch (error) {
    throw error.response;
  }
};

export const getAuthCodeApi = async (data) => {
  try {
    const response = await api.post("/api/email/send-verification", data);
    return response.data;
  } catch (error) {
    throw error.response;
  }
};

export const checkAuthCodeApi = async (data) => {
  try {
    const response = await api.post("/api/email/verify-code", data);
    return response.data;
  } catch (error) {
    throw error.response;
  }
};

export const leaveMemberApi = async () => {
  try {
    const response = await api.delete("/api/member");
    return response.data;
  } catch (error) {
    throw error.response;
  }
};

export const findPwdApi = async (data) => {
  try {
    const response = await api.post("/api/email/send-new-password", data);
    return response.data;
  } catch (error) {
    throw error.response;
  }
};

export const getMyInfo = async () => {
  try {
    const response = await api.get("/api/member/my-page");
    return response.data;
  } catch (error) {
    throw error.response;
  }
};

export const getUserInfo = async (userId) => {
  try {
    const response = await api.get(`/api/member/${userId}`);
    return response.data;
  } catch (error) {
    throw error.response;
  }
};

export const changePwdApi = async (data) => {
  try {
    const response = await api.put("/api/member/password", data);
    return response.data;
  } catch (error) {
    throw error.response;
  }
};

export const modifyInfo = async (data) => {
  try {
    const accessToken = localStorage.getItem("accessToken");
    const response = await axiosFormdata.put("/api/member", data, {
      headers: {
        "Content-Type": "multipart/form-data",
        Authorization: accessToken,
      },
    });
    return response.data;
  } catch (error) {
    throw error.response;
  }
};

// 보드
export const getLists = async (sortCondition, page = 0) => {
  try {
    const params = {
      page: page,
      size: 20,
      sortCondition: sortCondition,
    };

    const response = await axiosWithoutToken.get("/api/post/search", {
      params: params,
    });

    return response.data;
  } catch (error) {
    throw error.response;
  }
};

export const getListsWithSearch = async (
  searchKeyword,
  searchType,
  sortCondition,
  page = 0
) => {
  try {
    const params = {
      page: page,
      size: 20,
      searchKeyword: searchKeyword,
      searchType: searchType,
      sortCondition: sortCondition,
    };

    const response = await axiosWithoutToken.get("/api/post/search", {
      params: params,
    });

    return response.data;
  } catch (error) {
    throw error.response;
  }
};

export const getBoard = async (boardId) => {
  try {
    const response = await api.get(`/api/post/${boardId}`);
    return response.data;
  } catch (error) {
    throw error.response;
  }
};

export const createBoard = async (data) => {
  try {
    const response = await api.post("/api/post", data);
    return response.data;
  } catch (error) {
    throw error.response;
  }
};

export const updateBoard = async (boardId, data) => {
  try {
    const response = await api.put(`/api/post/${boardId}`, data);
    return response.data;
  } catch (error) {
    throw error.response;
  }
};

export const deleteBoard = async (boardId) => {
  try {
    const response = await api.delete(`/api/post/${boardId}`);
    return response.data;
  } catch (error) {
    throw error.response;
  }
};

export const likeBoard = async (data) => {
  try {
    const response = await api.post("/api/post/like", data);
    return response.data;
  } catch (error) {
    throw error.response;
  }
};

// 보드-댓글
export const createComment = async (boardId, data) => {
  try {
    const response = await api.post(`/api/post/${boardId}/comment`, data);
    return response.data;
  } catch (error) {
    throw error.response;
  }
};

export const updateComment = async (boardId, commentId, data) => {
  try {
    const response = await api.put(
      `/api/post/${boardId}/comment/${commentId}`,
      data
    );
    return response.data;
  } catch (error) {
    throw error.response;
  }
};

export const deleteComment = async (boardId, commentId) => {
  try {
    const response = await api.delete(
      `/api/post/${boardId}/comment/${commentId}`
    );
    return response.data;
  } catch (error) {
    throw error.response;
  }
};

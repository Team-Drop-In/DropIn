import axios from "axios";

const baseURL = process.env.REACT_APP_BaseURL;

const api = axios.create({
  baseURL: baseURL,
  headers: {
    "Content-Type": "application/json",
  },
  withCredentials: true,
});

// export const getHelloApi = async () => {
//   try {
//     const response = await api.get("/hello");
//     return response.data;
//   } catch (error) {
//     throw new Error(error.response);
//   }
// };

// 회원
export const loginApi = async (data) => {
  try {
    const res = await api.post("/api/login", data);
    const accessToken = res.headers["authorization"];
    localStorage.setItem("accessToken", accessToken);
    axios.defaults.headers["Authorization"] = accessToken;
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
    const response = await api.delete("/api/member", {
      headers: {
        Authorization: `${localStorage.getItem("accessToken")}`,
      },
    });
    return response.data;
  } catch (error) {
    throw error.response;
  }
};

export const findPwdApi = async (data) => {
  try {
    const response = await api.post("/api/email/send-new-password", data, {
      headers: {
        Authorization: `${localStorage.getItem("accessToken")}`,
      },
    });
    return response.data;
  } catch (error) {
    throw error.response;
  }
};

export const getMyInfo = async () => {
  try {
    const response = await api.get("/api/member/my-page", {
      headers: {
        Authorization: `${localStorage.getItem("accessToken")}`,
      },
    });
    return response.data;
  } catch (error) {
    throw error.response;
  }
};

export const getNewPwdApi = async (data) => {
  try {
    const response = await api.post("/api/email/send-new-password", data, {
      headers: {
        Authorization: `${localStorage.getItem("accessToken")}`,
      },
    });
    return response.data;
  } catch (error) {
    throw error.response;
  }
};

export const changePwdApi = async (data) => {
  try {
    const response = await api.put("/api/member/password", data, {
      headers: {
        Authorization: `${localStorage.getItem("accessToken")}`,
      },
    });
    return response.data;
  } catch (error) {
    throw error.response;
  }
};

export const modifyInfo = async (data) => {
  try {
    const response = await api.put("/api/member", data, {
      headers: {
        Authorization: `${localStorage.getItem("accessToken")}`,
        "Content-Type": "multipart/form-data",
      },
    });
    return response.data;
  } catch (error) {
    throw error.response;
  }
};

// 보드
export const getBoardLists = async (
  searchKeyword,
  searchType,
  sortCondition,
  page = 1
) => {
  try {
    const params = {
      page: page,
      size: 20, // Adjusted the size to match the desired size of 20
      searchKeyword: searchKeyword,
      searchType: searchType,
      sortCondition: sortCondition,
    };

    const response = await api.get("/api/post/search", {
      params: params,
      headers: {
        Authorization: `Bearer ${localStorage.getItem("accessToken")}`, // Adjusted Authorization header
      },
    });

    return response.data;
  } catch (error) {
    throw error.response;
  }
};

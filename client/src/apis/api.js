import axios from "axios";

const api = axios.create({
  baseURL: process.env.REACT_APP_BaseURL,
  headers: {
    "Content-Type": "application/json",
  },
  // withCredentials: true,
});

export const getHelloApi = async () => {
  try {
    const response = await api.get("/hello");
    return response.data;
  } catch (error) {
    console.error("API Error:", error);
    throw error;
  }
};

export const loginApi = async (data) => {
  try {
    const res = await api.post("/api/login", data);
    const accessToken = res.headers["authorization"];
    localStorage.setItem("accessToken", accessToken);
    axios.defaults.headers["Authorization"] = accessToken;
  } catch (error) {
    throw new Error(error.response.data.message);
  }
};

export const googleloginApi = async () => {
  try {
    const res = await axios.get(process.env.REACT_APP_googleURL);
    return res.data;
  } catch (error) {
    throw new Error(error.response.data.message);
  }
};

export const signupApi = async (data) => {
  try {
    const res = await api.post("/api/member", data);
    return res;
  } catch (error) {
    throw new Error(error.response.data.message);
  }
};

export const duplicateEmailApi = async ({ data }) => {
  try {
    const response = await api.post("/api/check-duplicate/email", { data });
    return response.data;
  } catch (error) {
    console.error("API Error:", error);
    throw error;
  }
};

export const duplicateNicknameApi = async (data) => {
  try {
    const response = await api.post("/api/check-duplicate/nickname", data);
    return response.data;
  } catch (error) {
    console.error("API Error:", error);
    throw error;
  }
};

export const getAuthCodeApi = async (data) => {
  try {
    const response = await api.post("/api/email/send-verification", data);
    return response.data;
  } catch (error) {
    console.error("API Error:", error);
    throw error;
  }
};

export const checkAuthCodeApi = async (data) => {
  try {
    const response = await api.post("/api/email/verify-code", data);
    return response.data;
  } catch (error) {
    console.error("API Error:", error);
    throw error;
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
    console.error("API Error:", error);
    throw error;
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
    console.error("API Error:", error);
    throw error;
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
    console.error("API Error:", error);
    throw error;
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
    console.error("API Error:", error);
    throw error;
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
    console.error("API Error:", error);
    throw error;
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
    console.error("API Error:", error);
    throw error;
  }
};

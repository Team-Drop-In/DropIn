import httpService from "./httpService";

export const getHelloApi = async () => {
  try {
    const response = await httpService.get(`${API_BASE_URL}/hello`);
    return response.data;
  } catch (error) {
    console.error("API Error:", error);
    throw error;
  }
};

export const loginApi = async (data) => {
  try {
    const res = await httpService.post("/api/login", data);
    return res;
  } catch (error) {
    throw new Error(error.response.data.message);
  }
};

export const signupApi = async (data) => {
  try {
    const res = await httpService.post("/api/member", data);
    return res;
  } catch (error) {
    throw new Error(error.response.data.message);
  }
};

export const duplicateEmailApi = async (data) => {
  try {
    const response = await httpService.get("/api/check-duplicate/email", data);
    return response.data;
  } catch (error) {
    console.error("API Error:", error);
    throw error;
  }
};

export const duplicateNicknameApi = async (data) => {
  try {
    const response = await httpService.get(
      "/api/check-duplicate/nickname",
      data
    );
    return response.data;
  } catch (error) {
    console.error("API Error:", error);
    throw error;
  }
};

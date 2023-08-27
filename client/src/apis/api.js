import axios from "axios";

const API_BASE_URL =
  "http://ec2-43-202-64-101.ap-northeast-2.compute.amazonaws.com:8080";

export const getHelloApi = async () => {
  try {
    const response = await axios.get(`${API_BASE_URL}/hello`);
    return response.data;
  } catch (error) {
    console.error("API Error:", error);
    throw error;
  }
};

const axiosApi = axios.create({
  baseURL: "https://www.pre-onboarding-selection-task.shop",
  headers: {
    "Content-Type": "application/json",
  },
});

export const loginApi = async (data) => {
  try {
    const res = await axiosApi.post("/api/login", data);
    return res;
  } catch (error) {
    throw new Error(error.response.data.message);
  }
};

export const signupApi = async (data) => {
  try {
    const res = await axiosApi.post("/api/member", data);
    return res;
  } catch (error) {
    throw new Error(error.response.data.message);
  }
};

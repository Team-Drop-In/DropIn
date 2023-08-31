import axios from "axios";
import httpService from "./httpService";

const axiosApi = axios.create({
  baseURL: "http://ec2-43-202-64-101.ap-northeast-2.compute.amazonaws.com:8080",
  headers: {
    "Content-Type": "application/json",
  },
});

export const getHelloApi = async () => {
  try {
    const response = await httpService.get("/hello");
    return response.data;
  } catch (error) {
    console.error("API Error:", error);
    throw error;
  }
};

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

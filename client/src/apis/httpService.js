import axios from "axios";

const httpService = axios.create();
httpService.defaults.baseURL =
  "http://ec2-43-202-64-101.ap-northeast-2.compute.amazonaws.com:8080";

httpService.interceptors.request.use((config) => {
  const accessToken = localStorage.getItem("accessToken");

  if (accessToken) {
    config.headers.Authorization = `Bearer ${accessToken}`;
  }

  return config;
});

httpService.interceptors.response.use((response) => {
  if (response.data.access_token) {
    localStorage.setItem("accessToken", response.data.access_token);
  }

  return response;
});

export default httpService;

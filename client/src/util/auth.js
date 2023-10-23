// 토큰 만료 검사
export const isAccessTokenExpired = () => {
  const accessToken = localStorage.getItem("accessToken");
  if (!accessToken) return true;

  const expirationTime = new Date(
    localStorage.getItem("accessTokenExpiration")
  );
  const currentTime = new Date();
  return currentTime >= expirationTime;
};

// 만료된 토큰 삭제 및 로그인 페이지로 리다이랙트
export const handleTokenExpiration = () => {
  localStorage.removeItem("accessToken");
  localStorage.removeItem("accessTokenExpiration");
  localStorage.removeItem("profileImage");

  window.location.href = "/login";
};

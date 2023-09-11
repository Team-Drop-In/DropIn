import styled from "styled-components";
import { useEffect, useState } from "react";
import { Container, Content } from "../../styles/style";
import MyInfo from "../../components/user/MyInfo";
import ModifyForm from "../../components/user/ModifyForm";
import { getMyInfo } from "../../apis/api";

const Mypage = () => {
  const [changeInfo, setChangeInfo] = useState(false);
  const [userInfo, setUserInfo] = useState({});

  useEffect(() => {
    getMyInfo()
      .then((res) => {
        setUserInfo(res.data);
      })
      .catch((error) => {
        console.error("로그인 실패:", error);
      });
  }, []);

  // console.log(userInfo);
  return (
    <Container>
      <Contain>
        <h2>내 정보</h2>
        {!changeInfo ? (
          <MyInfo setChangeInfo={setChangeInfo} data={userInfo} />
        ) : (
          <ModifyForm setChangeInfo={setChangeInfo} data={userInfo} />
        )}
      </Contain>
    </Container>
  );
};

export default Mypage;

const Contain = styled(Content)`
  height: 100vh;
  display: flex;
  flex-direction: column;

  h2 {
    color: white;
    font-size: 24px;
    margin-bottom: 50px;
  }
`;

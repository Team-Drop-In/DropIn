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
        console.error("마이페이지 불러오기 실패:", error);
      });
  }, []);

  console.log(userInfo);
  console.log(userInfo.username);
  console.log(userInfo.nickname);

  return (
    <Container>
      <Contain>
        <h2>내 정보</h2>
        {!changeInfo ? (
          <MyInfo
            setChangeInfo={setChangeInfo}
            data={userInfo}
            setUserInfo={setUserInfo}
          />
        ) : (
          <ModifyForm setChangeInfo={setChangeInfo} data={userInfo} />
        )}
      </Contain>
    </Container>
  );
};

export default Mypage;

const Contain = styled(Content)`
  height: calc(100vh - 60px);
  display: flex;
  flex-direction: column;

  h2 {
    color: white;
    font-size: 24px;
    margin-bottom: 50px;
  }
`;

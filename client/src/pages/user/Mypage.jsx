import styled from "styled-components";
import { useEffect, useState } from "react";
import { Container, Content } from "../../styles/style";
import MyInfo from "../../components/user/MyInfo";
import ModifyForm from "../../components/user/ModifyForm";
import { getMyInfo } from "../../apis/api";

const Mypage = () => {
  const [changeInfo, setChangeInfo] = useState(false);
  const [userInfo, setUserInfo] = useState({});
  const [username, setUsername] = useState("");
  const [userimgUrl, setUserimgUrl] = useState("");

  useEffect(() => {
    getMyInfo()
      .then((res) => {
        setUserInfo(res.data);
        setUsername(res.data.nickname);
        setUserimgUrl(res.data.profileImageUrl);
      })
      .catch((error) => {
        console.error("마이페이지 불러오기 실패:", error);
      });
  }, []);

  return (
    <Container>
      <Contain>
        <h2>내 정보</h2>
        {!changeInfo ? (
          <MyInfo
            username={username}
            userimgUrl={userimgUrl}
            setChangeInfo={setChangeInfo}
            data={userInfo}
            setUserInfo={setUserInfo}
          />
        ) : (
          <ModifyForm
            username={username}
            setUsername={setUsername}
            setUserimgUrl={setUserimgUrl}
            setChangeInfo={setChangeInfo}
            data={userInfo}
          />
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

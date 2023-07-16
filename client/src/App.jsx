import { useEffect, useState } from "react";
import axios from "axios";
import { Routes, Route } from "react-router-dom";
import Header from "./components/Header";
import Login from "./pages/user/Login";

const App = () => {
  // const [responseData, setResponseData] = useState(null);

  // useEffect(() => {
  //   fetchData();
  // }, []);

  // const fetchData = async () => {
  //   try {
  //     const response = await axios.get("");
  //     setResponseData(response.data);
  //   } catch (error) {
  //     console.error("Error fetching data:", error);
  //   }
  // };
  return (
    <div>
      <Header />
      <Routes>
        <Route path="/" element={<Login />} />
      </Routes>

      {/* {responseData ? (
        <div>
          <h2>API Response:</h2>
          <pre>{JSON.stringify(responseData, null, 2)}</pre>
        </div>
      ) : (
        <p>Loading...</p>
      )} */}
    </div>
  );
};

export default App;

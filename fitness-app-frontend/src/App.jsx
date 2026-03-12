import { BrowserRouter as Router, Navigate, Route, Routes } from "react-router-dom";
import { Button } from "@mui/material";
import { useContext, useEffect, useState } from "react";
import { useDispatch } from "react-redux";
import { setCredentials } from "./store/authSlice";
import { AuthContext } from "react-oauth2-code-pkce";
import Box from '@mui/material/Box';
import ActivityForm from "./components/ActivityForm";
import ActivityList from "./components/activityList";
import ActivityDetail from "./components/ActivityDetail";

const ActivityPage = () => {
  return (
    <Box component="section" sx={{ p: 2, border: '1px dashed grey' }}>
      <ActivityForm onActivityAdded={() => console.log("activity added")} />
      <ActivityList />
    </Box>
  );
};

function App() {
  const { token, tokenData, logIn, logOut, isAuthenticated } = useContext(AuthContext);
  const dispatch = useDispatch();
  const [ authReady, setAuthReady ] = useState(false);

  useEffect(() => {
    if (token) {
      dispatch(setCredentials({token, user: tokenData}));
      setAuthReady(true);
    }
  }, [token, tokenData, dispatch]);

  return (
    <Router>
      {!token ? (
        <Button variant="contained" color="primary"
          onClick={() => logIn()}
        >LOGIN</Button>
      ) : (
        // <div>
        //   <pre>{JSON.stringify(tokenData, null, 2)}</pre>
        //   <pre>{JSON.stringify(token, null, 2)}</pre>
        // </div>

        <Box component="section" sx={{ p: 2, border: '1px dashed grey' }}>
          <Routes>
            <Route path="/activities" element={<ActivityPage/>} />
            <Route path="/activities/:id" element={<ActivityDetail/>} />
            <Route path="/" element={token ? <Navigate to={"/activities"} replace /> : <div>Welcome Please Log in </div>} />
          </Routes>
        </Box>
      )}
    </Router>
  )
}

export default App

import React, { useEffect } from "react";
import { Typography, Box, Card, CardContent, Divider } from "@mui/material";
import { useParams } from "react-router-dom";
import { getActivitieDetail } from "../services/api";

const ActivityDetail = () => {
  const { id } = useParams();

  const [activity, setActivity] = React.useState(null);

  useEffect(() => {
    const fetchActivityDetail = async () => {
      try {
        const response = await getActivitieDetail(id);
        setActivity(response.data);
      } catch (error) {
        console.error("Error fetching activity detail:", error);
      }
    };

    fetchActivityDetail();
  }, [id]);

  if (!activity) {
    return <Typography>Loading...</Typography>;
  }

  return (
    <Box sx={{ maxWidth: 800, mx: "auto", p: 2 }}>
      <Card sx={{ mb: 2 }}>
        <CardContent>
          <Typography variant="h5">Activity Analysis</Typography>
          <Typography>Type: {activity.activityType}</Typography>
          <Typography>Date: {new Date(activity.createdAt).toLocaleString()}</Typography>
        </CardContent>
      </Card>

      <Card>
        <CardContent>
          <Typography variant="h5">AI Analysis</Typography>

          <Typography paragraph>{activity.analysisText}</Typography>

          <Divider sx={{ my: 2 }} />

          <Typography variant="h6">Improvements</Typography>
          {activity.improvements?.map((improvement, index) => (
            <Typography key={index}>• {improvement}</Typography>
          ))}

          <Divider sx={{ my: 2 }} />

          <Typography variant="h6">Suggestions</Typography>
          {activity.suggestions?.map((suggestion, index) => (
            <Typography key={index}>{suggestion}</Typography>
          ))}

          <Divider sx={{ my: 2 }} />

          <Typography variant="h6">Safety Guidelines</Typography>
          {activity.safetyGuidelines?.map((safety, index) => (
            <Typography key={index}>{safety}</Typography>
          ))}
        </CardContent>
      </Card>
    </Box>
  );
};

export default ActivityDetail;
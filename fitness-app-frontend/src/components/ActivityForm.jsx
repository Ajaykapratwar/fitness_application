import React, { useState } from "react";
import { Box,FormControl,InputLabel,Select,MenuItem,TextField, Button } from "@mui/material";
import { addActivity } from "../services/api";

const ActivityForm = ({ onActivityAdded }) => {
  const [activity, setActivity] = React.useState({
    type: "RUNNING",
    duration: "",
    caloriesBurned: "",
    additionalMetrics: {}
  });

  const handleSubmit = async (e) => {
    e.preventDefault();
    console.log("Submitting activity:", activity);

    try {
      await addActivity(activity);
      onActivityAdded();

      setActivity({
        type: "RUNNING",
        duration: "",
        caloriesBurned: "",
        additionalMetrics: {}
      });
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <Box component="form" onSubmit={handleSubmit} sx={{ mb: 4 }}>
      <FormControl fullWidth sx={{ mb: 2 }}>
        <InputLabel>Activity Type</InputLabel>

        <Select
          value={activity.type}
          label="Activity Type"
          onChange={(e) =>
            setActivity({ ...activity, type: e.target.value })
          }
        >
          <MenuItem value="RUNNING">Running</MenuItem>
          <MenuItem value="CYCLING">Cycling</MenuItem>
          <MenuItem value="SWIMMING">Swimming</MenuItem>
        </Select>
      </FormControl>

      <TextField fullWidth
        label="Duration (Minutes)"
        type="number"
        sx={{ mb: 2 }}
        value={activity.duration}
        onChange={(e) => setActivity({ ...activity, duration: e.target.value })}/>

      <TextField fullWidth
        label="Calories Burned"
        type="number"
        sx={{ mb: 2 }}
        value={activity.caloriesBurned}
        onChange={(e) => setActivity({ ...activity, caloriesBurned: e.target.value })}/>
      <Button variant="contained" color="primary" type="submit">Add Activity</Button>
    </Box>
  );
};

export default ActivityForm;
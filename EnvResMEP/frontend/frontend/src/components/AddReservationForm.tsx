// START GENAI@CHATGPT4

import {environments, userGroups} from "../sampleData.json";
import { useEffect, useState } from "react";
import { CalendarEvent, Reservation } from "../routes/ReservationsForCluster";
import moment from "moment";

// interface User {
//   id: number;
//   name: string;
// }

// interface GroupFormProps {
//   users: User[];
//   onSubmit: (groupName: string, userIds: number[]) => void;
// }

export interface ReservationProps {
    inputStartTime: string;
    setInputStartTime: React.Dispatch<React.SetStateAction<string>>;
    inputEndTime: string;
    setInputEndTime: React.Dispatch<React.SetStateAction<string>>;
    selectedEnvironmentId: number;
    setSelectedEnvironmentId: React.Dispatch<React.SetStateAction<number>>;
    events: CalendarEvent[];
    setEvents: React.Dispatch<React.SetStateAction<CalendarEvent[]>>;
    clusterId: number;
}

function AddReservationForm(props: ReservationProps) {
  const [selectedUserGroupId, setSelectedUserGroupId] = useState<number>(-1);

  const [errorMsg, setErrorMsg] = useState<string>("");

  const handleInputStartTimeChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    props.setInputStartTime(event.target.value)
  }

  const handleInputEndTimeChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    props.setInputEndTime(event.target.value)
  }

  const handleSelectedEnvironmentChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
    props.setSelectedEnvironmentId(Number(event.target.value));
  }

  const handleSelectedUsergroupChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
    setSelectedUserGroupId(Number(event.target.value));
  }

  // START GENAI@CHATGPT4
  useEffect(() => {
    if(userGroups.length > 0) {
      setSelectedUserGroupId(userGroups[0].id); // Set default value to id of first user group
    }
  }, [userGroups]);
  // END GENAI@CHATGPT4

  useEffect(() => {
    if(environments.length > 0) {
      props.setSelectedEnvironmentId(environments[0].id);
    }
  }, [environments]);

  const handleSubmit = (event: React.FormEvent) => {
    event.preventDefault();
    //onSubmit(groupName, selectedUserIds);
    console.log("Reservation was added!");
    //props.setSelectedEnvironmentId(-1);

    const selectedUserGroup = userGroups.find(userGroup => userGroup.id === selectedUserGroupId);
    const selectedUserGroupName: string = selectedUserGroup ? selectedUserGroup.name : "";
    // TODO: co gdy nic nie wybrane

    const newEvent: CalendarEvent = {
      id: 1,
      userGroupId: selectedUserGroupId,
      title: selectedUserGroupName, 
      resourceId: props.selectedEnvironmentId, 
      start: new Date(moment.utc(props.inputStartTime).format()), // TODO: understand "format()"
      end: new Date(moment.utc(props.inputEndTime).format())
    };

    const newReservation = {
      "userGroupID": selectedUserGroupId,
      "environmentID": props.selectedEnvironmentId,
      "clusterID": props.clusterId,
      "startTime": props.inputStartTime,
      "endTime": props.inputEndTime
    };

    console.log(newReservation);

    // props.setEvents((prev) => [...prev, newEvent]);

    fetch("http://localhost:8080/reservations", {
      method: 'POST', 
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(newReservation),
    }).then(response => {
      if (response.status === 409) {
        setErrorMsg("There is already other reservation!");
        setTimeout(() => setErrorMsg(""), 4000);
      } else if (!response.ok) {
        return response.json().then(data => {
          throw new Error(`Server error: ${data.response}`);
        });
      }
      else {
        props.setEvents((prev) => [...prev, newEvent]);
      }
      // continue processing the response
    })
    .catch(error => console.error(error.message));

  };


  return (
    <>
        <form onSubmit={handleSubmit} className="m-3 text-start">

            <div>
                <label className="form-label fw-bold">Start time:</label>
                <input type="datetime-local" className="form-control" value={props.inputStartTime} onInput={handleInputStartTimeChange} />
            </div>

            <div className="mt-3">
                <label className="form-label fw-bold">End time:</label>
                <input type="datetime-local" className="form-control" value={props.inputEndTime} onInput={handleInputEndTimeChange}/>
            </div>

            <div className="mt-3">
                <label className="form-label fw-bold">Select environment:</label>
                <select className="form-select" value={props.selectedEnvironmentId} onChange={handleSelectedEnvironmentChange}>
                    {environments.map(environment => (
                    <option key={environment.id} value={environment.id}>
                        {environment.name}
                    </option>
                    ))}
                </select>
            </div>

            <div className="mt-3">
                <label className="form-label fw-bold">Select user group:</label>
                <select className="form-select" onChange={handleSelectedUsergroupChange}>
                    {userGroups.map(userGroup => (
                        <option key={userGroup.id} value={userGroup.id}>
                            {userGroup.name}
                        </option>
                    ))}
                </select>
            </div>

            {errorMsg && <div className="alert alert-danger mt-3">{errorMsg}</div>}

            <button type="submit" className="btn btn-primary mt-5 w-100">Add</button>
        </form>
    </>

    
  );
};

export default AddReservationForm;

// END GENAI@CHATGPT4
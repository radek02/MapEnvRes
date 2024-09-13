// START GENAI@CHATGPT4

import {users} from "../sampleData.json";
import { useState } from "react";

// interface User {
//   id: number;
//   name: string;
// }

// interface GroupFormProps {
//   users: User[];
//   onSubmit: (groupName: string, userIds: number[]) => void;
// }

function AddUserGroup() {
  const [groupName, setGroupName] = useState('');
  const [selectedUserIds, setSelectedUserIds] = useState<number[]>([]);

  const handleSelectChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
    const userIds = Array.from(event.target.selectedOptions, option => Number(option.value));
    setSelectedUserIds(userIds);
  }

  const handleSubmit = (event: React.FormEvent) => {
    event.preventDefault();
    //onSubmit(groupName, selectedUserIds);
    alert("Usergroup \"" + groupName + "\" of " + selectedUserIds.length + " users was added");
    setGroupName('');
    setSelectedUserIds([]);
  };

  return (
    <>
        <form onSubmit={handleSubmit} className="m-3 text-start">
            <div>
                <label className="form-label fw-bold">Group name:</label>
                <input type="text" className="form-control" value={groupName} onChange={e => setGroupName(e.target.value)} required />
            </div>

            <div>
                <label className="form-label mt-3 fw-bold">Select users:</label>
                <select multiple className="form-select" value={selectedUserIds.map(String)} onChange={handleSelectChange}>
                    {users.map(user => (
                    <option key={user.id} value={user.id}>
                        {user.name}
                    </option>
                    ))}
                </select>
            </div>

            <button type="submit" className="btn btn-primary mt-3">Submit</button>
        </form>
    </>

    
  );
};

export default AddUserGroup;

// END GENAI@CHATGPT4
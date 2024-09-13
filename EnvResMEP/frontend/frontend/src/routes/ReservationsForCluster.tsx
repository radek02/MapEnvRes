import { Fragment, useCallback, useEffect, useMemo, useState } from "react";
import { clusters, reservationsByClusterId_1, environments, userGroups } from "../sampleData.json";
import { useParams } from "react-router-dom";
import UserHeader from "../components/UserHeader";
import "react-big-calendar/lib/css/react-big-calendar.css";
import { Calendar, SlotInfo, Views, momentLocalizer } from 'react-big-calendar';
import moment from "moment";
import 'moment-timezone';
import AddReservationForm, { ReservationProps } from "../components/AddReservationForm";

export interface Reservation {
    id: number;
    userGroupID: number;
    environmentID: number;
    clusterID: number;
    startTime: string;
    endTime: string;
}

export interface CalendarEvent {
    id: number;
    userGroupId: number;
    title: string;
    resourceId: number;
    start: Date;
    end: Date;
}

const colors: string[] = ['Navy', 'Indigo', 'DarkSlateGray', 'DarkOliveGreen', 'DarkGoldenRod', 'Maroon', 'Teal', 'DarkSlateBlue', 'MidnightBlue', 'DarkRed'];

// START GENAI@CHATGPT4
function transformReservationsToCalendarEvents(reservations: Reservation[]): CalendarEvent[] {
    return reservations.map(reservation => {
        const selectedUserGroup = userGroups.find(userGroup => userGroup.id === reservation.userGroupID);
        const selectedUserGroupName: string = selectedUserGroup ? selectedUserGroup.name : "";

        return {
            id: reservation.id,
            userGroupId: reservation.userGroupID,
            title: selectedUserGroupName,
            resourceId: reservation.environmentID,
            start: moment.utc(reservation.startTime).toDate(),
            end: moment.utc(reservation.endTime).toDate()
        };
    });
}
// END GENAI@CHATGPT4

function ReservationsForCluster() {
    const [inputStartTime, setInputStartTime] = useState<string>(new Date().toISOString());
    const [inputEndTime, setInputEndTime] = useState<string>(new Date().toISOString());
    const [selectedEnvironmentId, setSelectedEnvironmentId] = useState<number>(1);

    const [events, setEvents] = useState<CalendarEvent[]>([]);

    const [showAll, setShowAll] = useState(true);
    //const eventsArr: CalendarEvent[] = transformReservationsToCalendarEvents(reservationsByClusterId_1);
    //const [eventsArr, setEventsArr] = useState<CalendarEvent[]>([]);

    const clusterId = Number(useParams().clusterId);

    useEffect(() => {
        fetch(`http://localhost:8080/reservations/by-cluster-id/${clusterId}`)
          .then((response) => response.json())
          .then((data) => {console.log(data.response); return setEvents(transformReservationsToCalendarEvents(data.response));})
          .catch((error) => console.error(error));
      }, []);

    /* START GENAI@CHATGPT4 */
    // useEffect(() => {
    //     const myevents = transformReservationsToCalendarEvents(reservationsByClusterId_1);
    //     setEvents(myevents);
    // }, [reservationsByClusterId_1]);
    /* END GENAI@CHATGPT4 */
    //console.log(events);

    //const xddd = fetch("http://localhost:8080/reservations/by-cluster-id/1");
    //console.log(xddd);

    moment.tz.setDefault("UTC");
    const localizer = momentLocalizer(moment);

    // const localizer = globalizeLocalizer(Globalize);
    // const environmentId = Number(useParams().environmentId);

    // const environment = environments.find(environment => environment.id === environmentId);
    // const environmentName: string = environment ? environment.name : "";

    const cluster = clusters.find(cluster => cluster.id === clusterId);
    const clusterName: string = cluster ? cluster.name : "";
    //const [data, setData] = useState<Reservation[]>();

    //setData(reservationsByEnvironmentId_1);

    // TODO: maybe useCallback(..., [setEvents])
    const handleSelectSlot =
        (slotInfo: SlotInfo) => {
          setInputStartTime(slotInfo.start.toISOString().slice(0, 16));
          setInputEndTime(slotInfo.end.toISOString().slice(0, 16));
          setSelectedEnvironmentId(slotInfo.resourceId as number);
        };
    
    const handleSelectEvent = useCallback(
        (event: CalendarEvent) => {window.alert(event.title)},
        []
      );

    const reservationProps: ReservationProps = {
        inputStartTime,
        setInputStartTime,
        inputEndTime,
        setInputEndTime,
        selectedEnvironmentId,
        setSelectedEnvironmentId,
        events,
        setEvents,
        clusterId
      };

    const { defaultDate, scrollToTime, views } = useMemo(
        () => ({
          defaultDate: new Date(2024, 7, 16),
          scrollToTime: new Date(1970, 1, 1, 6),
          views: {
            day: true,
            work_week: true
          }
        }),
        []
      )
    
    const resourcesToShow = environments.filter(env => showAll || env.id === selectedEnvironmentId);

    return (
        <>
            <UserHeader />
            
            <div className="gap-3 align-items-start p-5 w-100 justify-content-start">
                <h2 className="text-center w-100">{clusterName}</h2>
                <div className="d-flex justify-content-start">
                    <div className="form-check form-check-inline">
                        <input 
                            type="checkbox"
                            checked={showAll}
                            onChange={e => setShowAll(e.target.checked)}
                            className="form-check-input"
                        />
                        <label className="form-check-label"> Show all environments</label>
                    </div>
                </div>
                <div className="d-flex gap-3">
                    <Calendar
                    defaultDate={defaultDate}
                    defaultView={Views.DAY}
                    events={events}
                    localizer={localizer}
                    resourceIdAccessor="id"
                    resources={resourcesToShow}
                    resourceTitleAccessor="name"
                    step={30}
                    views={views}
                    onSelectEvent={handleSelectEvent}
                    onSelectSlot={handleSelectSlot}
                    selectable
                    scrollToTime={scrollToTime}
                    eventPropGetter={(event) => {
                        const backgroundColor = colors[event.userGroupId % 10];
                        return { style: { backgroundColor } }
                    }}
                    />
                    <div className="bg-light p-4 rounded-4">
                        <AddReservationForm {...reservationProps}/>
                    </div>
                </div>
            </div>
        </>
    );
}

export default ReservationsForCluster;
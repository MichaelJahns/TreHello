const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp();

async function asyncDriver(task) {
    const payload = {
        notification: {
            title: "Task Updated",
            body: `${task.task} has been updated`
        }
    }

    const membersIDs = await getMemberIDs(task.teamReference);
    const memberProfiles = await getDocumentsByID("Profiles", membersIDs);
    pingTeamMembers(memberProfiles, payload);
}

async function getMemberIDs(teamReference) {
    const team = await admin.firestore().collection(`/Teams`).doc(teamReference).get();
    return team.data().membersIDs;
}

async function getDocumentsByID(path, ids) {
    console.log(ids);
    return admin.firestore().getAll(
        ids.map(id => admin.firestore().doc(`${path}/${id}`))
    )
}

function pingTeamMembers(memberProfiles, payload) {
    for (let profile of memberProfiles) {
        console.log(profile);
        const deviceID = profile.data().deviceID;
        admin.messaging().sendToDevice(deviceID, payload);
    }
}

exports.notifyTeam = functions.firestore
    .document("/Tasks/{taskId}")
    .onUpdate((change, context) => {
        const task = change.after.data();
        return asyncDriver(task);
    });


    // https://stackoverflow.com/questions/46818082/error-http-error-400-the-request-has-errors-firebase-firestore-cloud-function



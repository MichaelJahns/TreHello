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

    const memberIDS = await getMemberIDs(task.teamReference);
    const memberProfiles = await getDocumentsByID("Profiles", memberIDS);
    pingTeamMembers(memberProfiles, payload);
}

async function getMemberIDs(teamReference) {
    const team = await admin.firestore().collection(`/Teams/${teamReference}`).get();
    return team.data().memberIDS;
}

async function getDocumentsByID(path, ids) {
    return firestore.getAll(
        ids.map(id => firestore.doc(`${path}/${id}`))
    )
}

function pingTeamMembers(memberProfiles, payload) {
    for (profile in memberProfiles) {
        const deviceID = profile.deviceID;
        admin.messaging().sendToDevice(deviceID, payload);
    }
}

exports.notifyTeam = functions.firestore
    .document("/Tasks/{taskId}")
    .onUpdate((snap, context) => {
        const task = snap.data();
        return asyncDriver(task);
    });



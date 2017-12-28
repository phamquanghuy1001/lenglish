

export class Message {
    constructor(
        public type: string,
        public sender: string,
        public content: string,
        public image: string,
        public time: any,
        public iconContentType: any
    ) {
    }
}

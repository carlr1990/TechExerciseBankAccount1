package tech.excercise.entity;


import java.util.List;

public class SubmissionPrintWrapper {
    private final SubmissionPrint submission;

    public SubmissionPrintWrapper(List<AuditBatch> submission) {
        this.submission = new SubmissionPrint(submission);
    }
    public SubmissionPrint getSubmission() {
        return submission;
    }
}


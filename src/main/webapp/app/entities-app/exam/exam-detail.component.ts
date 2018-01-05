import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

import { Exam } from './exam.model';
import { ExamService } from './exam.service';
import { Question, QuestionService } from '../question';
import { Answer, AnswerService } from '../answer';
import { Result } from '../answer/result.model';

@Component({
    selector: 'jhi-exam-detail',
    templateUrl: './exam-detail.component.html',
    styleUrls: [
        './exam-detail.component.scss'
    ]
})
export class ExamDetailComponent implements OnInit, OnDestroy {

    exam: Exam;
    private subscription: Subscription;
    private eventSubscriber: Subscription;
    questions: Question[];
    answer: Answer;
    answers: Answer[];
    submitAnswers: Answer[];
    count: number;
    resultLesson: Result;
    indexQuestion: number;
    content: string;
    status: String = 'init';
    result: Result;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private examService: ExamService,
        private route: ActivatedRoute,
        private questionService: QuestionService,
        private answerService: AnswerService
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
            this.loadQuestion(params['id']);
        });
        this.registerChangeInExams();
        this.count = 0;
        this.answer = new Answer();
    }

    load(id) {
        this.examService.find(id).subscribe((exam) => {
            this.exam = exam;
        });
    }

    loadQuestion(id: number) {
        this.questionService.queryByExam(id).subscribe(
            (res: ResponseWrapper) => {
                this.questions = res.json;
                this.submitAnswers = new Array(this.questions.length);
                this.indexQuestion = 0;
                this.loadAnswers(this.questions[this.indexQuestion].id);
            }
        );
    }

    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInExams() {
        this.eventSubscriber = this.eventManager.subscribe(
            'examListModification',
            (response) => this.load(this.exam.id)
        );
    }

    // new
    onPlay() {
        console.log('data:' + this.questions[this.indexQuestion].resourceContentType + ';base64,' + this.questions[this.indexQuestion].resource);
        const resource = 'data:' + this.questions[this.indexQuestion].resourceContentType + ';base64,' + this.questions[this.indexQuestion].resource;
        const audio = new Audio(resource);
        audio.play();
    }

    onCheck() {
        const question: Question = this.questions[this.indexQuestion];
        const currentAnswer = new Answer();
        if (question.questionType + '' === 'SELECTION') {
            currentAnswer.questionId = question.id;
            currentAnswer.id = this.answer.id;
            if (this.answer.result === true) {
                this.status = 'success';
                currentAnswer.result = true;
            } else {
                this.status = 'fail';
                currentAnswer.result = false;
            }
        } else if (question.questionType + '' === 'LISTENING') {
            currentAnswer.questionId = question.id;
            currentAnswer.content = this.content;
            if (this.content === undefined || this.content === '') {
                this.status = 'fail';
                currentAnswer.result = false;
            } else if (question.content.toLowerCase().trim() === this.content.toLowerCase().trim()) {
                this.status = 'success';
                currentAnswer.result = true;
            } else {
                this.status = 'fail';
                currentAnswer.result = false;
            }
        } else if (question.questionType + '' === 'TRANSLATION') {
            currentAnswer.questionId = question.id;
            currentAnswer.content = this.content;
            this.status = 'fail';
            currentAnswer.result = false;
            for (const ans of this.answers) {
                if (this.content === undefined || this.content === '') {
                    this.status = 'fail';
                    currentAnswer.result = false;
                } else if (ans.content.toLowerCase().trim() === this.content.toLowerCase().trim()) {
                    this.status = 'success';
                    currentAnswer.result = true;
                }
            }
        }
        this.submitAnswers[this.indexQuestion] = currentAnswer;
    }

    onNext() {
        if (this.indexQuestion < this.questions.length - 1) {
            this.indexQuestion++;
            this.loadAnswers(this.questions[this.indexQuestion].id);
            this.status = 'init';
            this.content = '';
        } else {
            for (const ans of this.submitAnswers) {
                if (ans && ans.result === true) {
                    this.count++;
                }
            }
            this.status = 'finish';
        }
        this.answer = new Answer();
    }

    loadAnswers(questionId: number) {
        // load answer
        this.answerService.queryByQuestion(questionId).subscribe((res: ResponseWrapper) => {
            this.answers = res.json;
        });
    }

    onChooseAnswer(answer: Answer) {
        this.answer = answer;
    }

    onSubmitAnswers() {
        console.log(this.submitAnswers);
        this.answerService.submitAnswerByExam(this.exam.id, this.submitAnswers).subscribe((res: Result) => {
            this.result = res;
        });
    }

    onSkip() {
        if (this.indexQuestion < this.questions.length - 1) {
            this.indexQuestion++;
            this.loadAnswers(this.questions[this.indexQuestion].id);
            this.status = 'init';
            this.content = '';
        } else {
            for (const ans of this.submitAnswers) {
                if (ans && ans.result === true) {
                    this.count++;
                }
            }
            this.status = 'finish';
        }
        this.answer = new Answer();
    }
}

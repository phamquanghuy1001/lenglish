import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Question } from './question.model';
import { QuestionService } from './question.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

import { Answer, AnswerService, Result } from '../answer';
import { QuestionType } from '../../entities-app/question/index';

@Component({
    selector: 'jhi-question',
    templateUrl: './question.component.html',
    styleUrls: [
        'question.component.scss'
    ],
})
export class QuestionComponent implements OnInit, OnDestroy {
    lessonId: number;
    currentAccount: any;
    questions: Question[];
    error: any;
    success: any;
    eventSubscriber: Subscription;
    routeData: any;
    links: any;
    totalItems: any;
    queryCount: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;
    indexQuestion: number;
    content: string;
    status: String = 'init';
    answer: Answer;
    answers: Answer[];
    submitAnswers: Answer[];
    count: number;
    result: Result;

    constructor(
        private questionService: QuestionService,
        private parseLinks: JhiParseLinks,
        private jhiAlertService: JhiAlertService,
        private principal: Principal,
        private activatedRoute: ActivatedRoute,
        private dataUtils: JhiDataUtils,
        private router: Router,
        private eventManager: JhiEventManager,
        private answerService: AnswerService
    ) { }

    loadByLesson(id: number) {
        this.questionService.queryByLesson(id).subscribe(
            (res: ResponseWrapper) => this.onSuccess(res.json, res.headers),
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    ngOnInit() {
        this.count = 0;
        this.activatedRoute.params.subscribe((params) => {
            this.lessonId = params['id'];
        });
        this.loadByLesson(this.lessonId);
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInQuestions();
        this.answer = new Answer();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInQuestions() {
        this.eventSubscriber = this.eventManager.subscribe('questionListModification', (response) => this.loadByLesson(this.lessonId));
    }

    private onSuccess(data, headers) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        this.queryCount = this.totalItems;
        // this.page = pagingParams.page;
        this.questions = data;
        this.submitAnswers = new Array(this.questions.length);
        this.indexQuestion = 0;
        if (this.questions.length > 0) {
            this.loadAnswers(this.questions[this.indexQuestion].id);
        }
    }
    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
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
        this.answerService.submitAnswer(this.lessonId, this.submitAnswers).subscribe((res: Result) => {
            this.result = res;
            this.eventManager.broadcast({ name: 'getUser', content: 'OK' });
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

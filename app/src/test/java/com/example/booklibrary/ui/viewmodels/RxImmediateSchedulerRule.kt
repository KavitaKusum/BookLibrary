package com.example.booklibrary.ui.viewmodels

import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Function
import io.reactivex.rxjava3.functions.Supplier
import io.reactivex.rxjava3.internal.schedulers.ExecutorScheduler
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit


class RxImmediateSchedulerRule : TestRule {
    private val immediate: Scheduler = object : Scheduler() {
        override fun scheduleDirect(run: Runnable, delay: Long, unit: TimeUnit): Disposable {
            return super.scheduleDirect(run, 0, unit)
        }

        override fun createWorker(): Worker {
            return ExecutorScheduler.ExecutorWorker(
                { obj: Runnable? -> obj!!.run() }, true, true
            )
        }
    }

    override fun apply(base: Statement, description: Description?): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                RxJavaPlugins.setInitIoSchedulerHandler(Function { scheduler: Supplier<Scheduler?>? -> immediate })
                RxJavaPlugins.setInitComputationSchedulerHandler(Function { scheduler: Supplier<Scheduler?>? -> immediate })
                RxJavaPlugins.setInitNewThreadSchedulerHandler(Function { scheduler: Supplier<Scheduler?>? -> immediate })
                RxJavaPlugins.setInitSingleSchedulerHandler(Function { scheduler: Supplier<Scheduler?>? -> immediate })
                RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler: Callable<Scheduler?>? -> immediate }

                try {
                    base.evaluate()
                } finally {
                    RxJavaPlugins.reset()
                    RxAndroidPlugins.reset()
                }
            }
        }
    }
}


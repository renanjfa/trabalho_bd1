import React, { useState, useEffect } from "react";
import "./style/Dashboard.css";
import { Bar, Pie } from "react-chartjs-2";
import { Chart as ChartJS, CategoryScale, LinearScale, BarElement, ArcElement, Title, Tooltip, Legend } from "chart.js";

import Header from "../components/Header";
import SideBar from "../components/SideBar";
import { getInfoSistema, getVersoesDatasets, getDatasetsMais, getContribuicoes } from "../services/relatoriosService";

ChartJS.register(CategoryScale, LinearScale, BarElement, ArcElement, Title, Tooltip, Legend);

export default function Dashboard() {
    const [sistema, setSistema] = useState(null);
    const [versoesDatasets, setVersoesDatasets] = useState([]);
    const [datasetsMais, setDatasetsMais] = useState([]);
    const [contribuicoes, setContribuicoes] = useState([]);
    const [orderBy, setOrderBy] = useState("acessos");

    useEffect(() => {
        carregarDados();
    }, []);

    useEffect(() => {
        carregarDatasetsMais();
    }, [orderBy]);

    async function carregarDados() {
        try {
            const sys = await getInfoSistema();
            setSistema(sys);
            const vd = await getVersoesDatasets();
            setVersoesDatasets(vd);
            const dm = await getDatasetsMais(orderBy);
            setDatasetsMais(dm);
            const ct = await getContribuicoes();
            setContribuicoes(ct);
        } catch (e) {
            console.error(e.message);
        }
    }

    async function carregarDatasetsMais() {
        try {
            const dm = await getDatasetsMais(orderBy);
            setDatasetsMais(dm);
        } catch (e) {
            console.error(e.message);
        }
    }

    const barData = {
        labels: versoesDatasets.map(v => v.nomeDataset),
        datasets: [{
            label: "Quantidade de Versões",
            data: versoesDatasets.map(v => v.qntVersoes),
            backgroundColor: "#3cb5a0",
        }],
    };

    const barOptions = {
        indexAxis: "y",
        responsive: true,
        plugins: {
            legend: { display: false },
        },
        scales: {
            x: { beginAtZero: true },
        },
    };

    const pieColors = ["#ef4b2d", "#3cb5a0", "#f59e0b", "#6366f1", "#ec4899", "#14b8a6", "#8b5cf6", "#f97316"];

    const pieData = {
        labels: contribuicoes.map(c => c.nomeUsuario),
        datasets: [{
            data: contribuicoes.map(c => c.contribuicaoPercentual),
            backgroundColor: pieColors.slice(0, contribuicoes.length),
        }],
    };

    const pieOptions = {
        responsive: true,
        plugins: {
            legend: { position: "right", labels: { font: { size: 11 } } },
            tooltip: {
                callbacks: {
                    label: (context) => context.label + ": " + context.parsed + "%",
                },
            },
        },
    };

    return (
        <div className="container">
            <Header />
            <div className="content">
                <SideBar activeSection={"dashboard"} />

                <main className="dashboard-main">
                    <h1 className="dashboard-title">Dashboard</h1>

                    <div className="dashboard-grid">
                        <div className="dashboard-left">

                            <div className="dashboard-card">
                                <div className="card-header">
                                    <h3>Datasets Mais Visualizados e Baixados</h3>
                                    <div className="order-buttons">
                                        <button
                                            className={orderBy === "acessos" ? "order-btn active" : "order-btn"}
                                            onClick={() => setOrderBy("acessos")}>
                                            Acessos
                                        </button>
                                        <button
                                            className={orderBy === "downloads" ? "order-btn active" : "order-btn"}
                                            onClick={() => setOrderBy("downloads")}>
                                            Downloads
                                        </button>
                                    </div>
                                </div>
                                <table className="dashboard-table">
                                    <thead>
                                        <tr>
                                            <th>Ranking</th>
                                            <th>Dataset</th>
                                            <th>Autor</th>
                                            <th>Acessos</th>
                                            <th>Downloads</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        {datasetsMais.map((d, i) => (
                                            <tr key={i}>
                                                <td>{i + 1}°</td>
                                                <td>{d.nomeDataset}</td>
                                                <td>{d.nomeUsuario}</td>
                                                <td>{d.qntAcessos}</td>
                                                <td>{d.qntDownloads}</td>
                                            </tr>
                                        ))}
                                    </tbody>
                                </table>
                            </div>

                            <div className="dashboard-card">
                                <h3>Contribuições</h3>
                                <table className="dashboard-table">
                                    <thead>
                                        <tr>
                                            <th>Ranking</th>
                                            <th>Usuário</th>
                                            <th>Datasets Registrados</th>
                                            <th>Versões Criadas</th>
                                            <th>Contribuição</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        {contribuicoes.map((c, i) => (
                                            <tr key={i}>
                                                <td>{i + 1}°</td>
                                                <td>{c.nomeUsuario}</td>
                                                <td>{c.datasetsCriados}</td>
                                                <td>{c.versoesCriadas}</td>
                                                <td>{c.contribuicaoPercentual}%</td>
                                            </tr>
                                        ))}
                                    </tbody>
                                </table>

                                {contribuicoes.length > 0 && (
                                    <div className="chart-container">
                                        <Pie data={pieData} options={pieOptions} />
                                    </div>
                                )}
                            </div>

                        </div>

                        <div className="dashboard-right">

                            <div className="dashboard-card">
                                <h3>Sistema</h3>
                                <div className="sistema-info">
                                    <p>Total Datasets <span>{sistema?.qntDatasets ?? 0}</span></p>
                                    <p>Total Versões <span>{sistema?.qntVersoes ?? 0}</span></p>
                                    <p>Total Usuários <span>{sistema?.qntUsuarios ?? 0}</span></p>
                                </div>
                            </div>

                            <div className="dashboard-card">
                                <h3>Versões</h3>
                                <table className="dashboard-table table-small">
                                    <thead>
                                        <tr>
                                            <th>Dataset</th>
                                            <th>Autor</th>
                                            <th>Qtd Versões</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        {versoesDatasets.map((v, i) => (
                                            <tr key={i}>
                                                <td>{v.nomeDataset}</td>
                                                <td>{v.nomeUsuario}</td>
                                                <td>{v.qntVersoes}</td>
                                            </tr>
                                        ))}
                                    </tbody>
                                </table>

                                {versoesDatasets.length > 0 && (
                                    <div className="chart-container">
                                        <Bar data={barData} options={barOptions} />
                                    </div>
                                )}
                            </div>

                        </div>
                    </div>
                </main>
            </div>
        </div>
    );
}